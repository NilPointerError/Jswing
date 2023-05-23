package entity;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BrowseButton extends JButton {
    private File selectedFile;

    public BrowseButton(String buttonText) {
        super(buttonText);
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public void onFileSelected(File file) {
        // 默认实现为空，需要在具体使用时进行覆写
    }
}
