package jencrypter.Core;

import java.awt.Color;
import java.io.File;
import jencrypter.MainPanel;

public class Encrypter {

    private static final int MODE = 0;
    private static final int KEY = 1;
    private static final int FILE = 2;
    private static final String ENCRYPT_MODE = "Enc";
    private static final String DECRYPT_MODE = "Dec";

    public static void launch(String[] args) {

        String fileName = args[FILE];

        String key = args[KEY];
        File inputFile = new File(args[FILE]);
        File encryptedFile = new File(fileName);
        File decryptedFile = new File(fileName);

        try {
            if (args[MODE].equals(ENCRYPT_MODE)) {
                Methods.encrypt(key, inputFile, encryptedFile);
                MainPanel.writeInConsole("File encrypted.");

            } else if (args[MODE].equals(DECRYPT_MODE)) {
                Methods.decrypt(key, encryptedFile, decryptedFile);
                MainPanel.writeInConsole("File decrypted.");

            }

        } catch (EncrypterException ex) {
            MainPanel.getConsole().setForeground(Color.red);
            MainPanel.writeInConsole(ex.getMessage());
            ex.printStackTrace();
        }

    }

}
