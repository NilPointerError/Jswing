package entity;

import java.io.File;

public class KeyBrowseButton extends BrowseButton {
    public KeyBrowseButton(String buttonText) {
        super(buttonText);
    }

    @Override
    public void onFileSelected(File file) {
        // 实现对密钥文件的处理逻辑
        System.out.println("处理密钥文件：" + file.getAbsolutePath());
    }
}


