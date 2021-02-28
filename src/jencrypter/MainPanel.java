package jencrypter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import jencrypter.Core.Encrypter;

public class MainPanel extends JPanel {

    private int DIM = 500;

    private static final int MODE = 0;
    private static final int KEY = 1;
    private static final int FILE = 2;
    private static final int KEY_LENGHT_ERROR = -1;
    private static final String ENCRYPT_MODE = "Enc";
    private static final String DECRYPT_MODE = "Dec";

    private static final String NO_FILE_SELECTED_ERROR = "Error: No file chosen.";
    private static final String INVALID_KEY_ERROR = "Error: Key lenght is not 16.";
    private static final String DIFFERENT_KEYS_ERROR = "Error: Keys are different.";
    private static final String OUT_OF_MEMORY_ERROR = "Error: File too big. Use 64 bit laucher.";

    private String[] coreParams = new String[3];

    private static JTextArea console;
    private JTextField key, repeatKey;
    private JButton fileChooserButton;
    private JButton encryptButton;
    private JButton decryptButton;

    private JFileChooser fileChooser;
    private FileSystemView fsv = FileSystemView.getFileSystemView();
    private File selectedFile;
    private boolean fileReady;

    public MainPanel() {

        this.setSize(DIM, DIM);
        this.setLayout(null);

        this.key = new JTextField("KEY (16 bit)");
        this.key.setBounds(50, 100, 200, 30);

        this.repeatKey = new JTextField("REPEAT KEY");
        this.repeatKey.setBounds(50, 150, 200, 30);

        this.add(key);
        this.add(repeatKey);

        console = new JTextArea("Console\n");
        console.setBackground(Color.darkGray);
        console.setForeground(Color.white);
        console.setBounds(50, 200, 400, 250);
        this.add(console);

        this.fileChooserButton = new JButton("Choose File");
        this.fileChooserButton.setBackground(Color.yellow);
        this.fileChooserButton.setBounds(190, 35, 120, 30);
        this.fileChooserButton.addActionListener(new ChooseFileListener());
        this.add(this.fileChooserButton);

        this.encryptButton = new JButton("Encrypt");
        this.encryptButton.setBackground(Color.green);
        this.encryptButton.setBounds(270, 100, 80, 80);
        this.encryptButton.addActionListener(new EncryptListener());
        this.add(this.encryptButton);

        this.decryptButton = new JButton("Decrypt");
        this.decryptButton.setBackground(Color.red);
        this.decryptButton.setBounds(370, 100, 80, 80);
        this.decryptButton.addActionListener(new DecryptListener());
        this.add(this.decryptButton);

        this.fileReady = false;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, DIM, DIM);

    }

    public static JTextArea getConsole() {
        return console;
    }

    public static void writeInConsole(String text) {
        console.append("\n" + text);
    }

    public static void clearConsole() {
        console.setText("Console\n");
    }

    private void openFileChooser() {

        this.clearConsole();
        this.fileChooser = new JFileChooser(this.fsv);
        int returnValue = this.fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            this.selectedFile = this.fileChooser.getSelectedFile();
            this.fileReady = true;
            console.setForeground(Color.white);
            writeInConsole("Selected file:\n" + this.selectedFile.getAbsolutePath());
        } else if (returnValue == JFileChooser.CANCEL_OPTION) {
            this.selectedFile = null;
            this.fileReady = false;
        }
    }

    private boolean fileChosen() {
        if (this.fileReady) {
            return true;
        } else {
            console.setForeground(Color.red);
            return false;
        }
    }

    private int checkKey() {
        if (this.key.getText().length() != 16) {
            console.setForeground(Color.red);
            return KEY_LENGHT_ERROR;
        } else {
            return 0;
        }
    }

    private boolean keysEquals() {
        if (this.key.getText().equals(this.repeatKey.getText())) {
            return true;
        } else {
            console.setForeground(Color.red);
            return false;
        }
    }

    private void encrypt() {

        try {

            if (this.fileChosen()) {
                if (this.checkKey() != KEY_LENGHT_ERROR) {
                    if (this.keysEquals()) {
                        console.setForeground(Color.white);
                        this.coreParams[MODE] = ENCRYPT_MODE;
                        this.coreParams[KEY] = this.key.getText();
                        this.coreParams[FILE] = this.selectedFile.getAbsolutePath();
                        Encrypter.launch(coreParams);
                    } else {
                        writeInConsole(DIFFERENT_KEYS_ERROR);
                    }
                } else {
                    writeInConsole(INVALID_KEY_ERROR);
                }
            } else {
                writeInConsole(NO_FILE_SELECTED_ERROR);
            }

        } catch (OutOfMemoryError exc) {
            console.setForeground(Color.red);
            writeInConsole(OUT_OF_MEMORY_ERROR);
        }

    }

    private void decrypt() {

        try {

            if (this.fileChosen()) {
                if (this.checkKey() != KEY_LENGHT_ERROR) {
                    if (this.keysEquals()) {
                        console.setForeground(Color.white);
                        this.coreParams[MODE] = DECRYPT_MODE;
                        this.coreParams[KEY] = this.key.getText();
                        this.coreParams[FILE] = this.selectedFile.getAbsolutePath();
                        Encrypter.launch(coreParams);
                    } else {
                        console.setForeground(Color.red);
                        writeInConsole(DIFFERENT_KEYS_ERROR);
                    }
                } else {
                    console.setForeground(Color.red);
                    writeInConsole(INVALID_KEY_ERROR);
                }
            } else {
                console.setForeground(Color.red);
                writeInConsole(NO_FILE_SELECTED_ERROR);
            }

        } catch (OutOfMemoryError exc) {
            console.setForeground(Color.red);
            writeInConsole(OUT_OF_MEMORY_ERROR);
        }

    }

    public class ChooseFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            openFileChooser();
        }

    }

    public class EncryptListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            encrypt();
        }

    }

    public class DecryptListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            decrypt();
        }

    }

}
