package utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.SecureRandom;

public class FileEncryptionWithRandomKey {

    public static void encrypt(File inputFile, File outputFile) throws Exception {
        // 生成随机密钥
        SecretKey secretKey = generateRandomKey();

        // 使用随机密钥加密文件
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        byte[] iv = generateRandomIV();
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(iv);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                byte[] encryptedData = cipher.update(buffer, 0, len);
                outputStream.write(encryptedData);
            }
            byte[] finalBlock = cipher.doFinal();
            outputStream.write(finalBlock);
        }

        // 保存随机密钥
        saveKeyToFile(secretKey, new File("key.bin"));
    }

    public static String decrypt(File inputFile, File keyFile) throws Exception {
        // 从文件中读取随机密钥
        SecretKey secretKey = readKeyFromFile(keyFile);

        // 使用随机密钥解密文件
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] iv = new byte[12];
        inputStream.read(iv);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

        System.out.println("decrypt start");

        String decrytedContent = "";
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) > 0) {
            byte[] decryptedData = cipher.update(buffer, 0, len);
            String temp = new String(decryptedData);
            decrytedContent += temp;
        }
        System.out.println("decrytedContent:"+decrytedContent);
        byte[] finalBlock = cipher.doFinal();
        decrytedContent += new String(finalBlock);
        System.out.println("decrytedContent:"+decrytedContent);
        return decrytedContent;
    }

    private static SecretKey generateRandomKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(256, secureRandom);
        return keyGenerator.generateKey();
    }

    private static byte[] generateRandomIV() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[12];
        secureRandom.nextBytes(iv);
        return iv;
    }

    private static void saveKeyToFile(SecretKey secretKey, File file) throws Exception {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            byte[] encodedKey = secretKey.getEncoded();
            outputStream.writeObject(encodedKey);
        }
    }

    private static SecretKey readKeyFromFile(File file) throws Exception {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            byte[] encodedKey = (byte[]) inputStream.readObject();
            return new SecretKeySpec(encodedKey, "AES");
        }
    }
}