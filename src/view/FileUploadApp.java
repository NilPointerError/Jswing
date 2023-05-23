package view;

import entity.BrowseButton;
import listener.BrowseButtonListener;
import listener.runButtonListener;
import utils.FileEncryptionWithRandomKey;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileUploadApp extends JFrame {

    private BrowseButton cipherBrowseButton;
    private JButton keyBrowseButton;
    private JTextArea fileContentArea;

    private JButton runButton;


    private File cipherFile;
    private File keyFile;

    public FileUploadApp() {
        setTitle("File Decryption");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(410, 300);
        setLayout(null);

        // 创建文件内容显示区域
        fileContentArea = new JTextArea();
        fileContentArea.setBounds(20, 70, 360, 150);
        fileContentArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(fileContentArea);
        scrollPane.setBounds(20, 70, 360, 150);
        add(scrollPane);

        cipherBrowseButton = createBrowseButton("upload", 20, 20);

        keyBrowseButton = createBrowseButton("key", 160, 20);

        runButton = createRunButton("run", 300, 20);


        // 窗口居中显示
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = 400; // 窗口宽度
        int frameHeight = 200; // 窗口高度
        setLocation((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2);

        setVisible(true);
    }

    private BrowseButton createBrowseButton(String name, int x, int y) {
        // 创建选择文件按钮
        JLabel filePathLab = new JLabel();

        BrowseButton browseButton = new BrowseButton(name);
        browseButton.setBounds(x, y, 100, 30);
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                //FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
                //fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (e.getActionCommand() == "upload") {
                        cipherFile = selectedFile;
                    } else if (e.getActionCommand() == "key") {
                        keyFile = selectedFile;
                    }
                    filePathLab.setText(selectedFile.getAbsolutePath());
                    System.out.println("selectedFile"+selectedFile);
                }
            }
        });
        add(browseButton);
        System.out.println("x"+x);
        System.out.println("y"+y);
        filePathLab.setBounds(x, y+35, 200, 15);
        add(filePathLab);
        return browseButton;
    }

    private JButton createRunButton(String name, int x, int y) {
        // 创建选择文件按钮
        JButton button = new JButton(name);
        button.setBounds(x, y, 80, 30);
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cipherFile == null || keyFile == null) {
                    JOptionPane.showMessageDialog(null, "缺少文件", "提示", JOptionPane.WARNING_MESSAGE);
                } else {
                    String decrytedContent = null;
                    try {
                        decrytedContent = FileEncryptionWithRandomKey.decrypt(cipherFile, keyFile);
                        fileContentArea.setText(decrytedContent);
                        //add(fileContentArea);
                    } catch (Exception ex) {
                        if (ex != null) {
                            System.out.println(ex);
                            JOptionPane.showMessageDialog(null, "运行失败，文件不匹配", "提示", JOptionPane.WARNING_MESSAGE);
                        }
                        //throw new RuntimeException(ex);
                    }
                }
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FileUploadApp();
            }
        });
    }
}
