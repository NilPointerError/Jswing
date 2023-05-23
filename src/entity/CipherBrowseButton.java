package entity;

import java.io.File;

public class CipherBrowseButton extends BrowseButton {
    public CipherBrowseButton(String buttonText) {
        super(buttonText);
    }

    @Override
    public void onFileSelected(File file) {
        // 实现对密文文件的处理逻辑
        System.out.println("处理密文文件：" + file.getAbsolutePath());
    }
}

