package listener;

import utils.FileEncryptionWithRandomKey;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BrowseButtonListener implements ActionListener {


    private JTextArea fileContentArea;

    private JLabel filePathLab;

    public BrowseButtonListener(JLabel filePathLab, JTextArea fileContentArea) {
        this.fileContentArea = fileContentArea;
        this.filePathLab = filePathLab;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePathLab.setText(selectedFile.getAbsolutePath());
            //System.out.println(selectedFile.getAbsolutePath());

            

        }
    }
}
