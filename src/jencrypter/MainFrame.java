package jencrypter;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
    
    private int DIM = 500;

    MainPanel mainPanel;

    public MainFrame() {
        this.setSize(DIM, DIM);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setTitle("J Encrypter");

        this.mainPanel = new MainPanel();
        this.getContentPane().add(this.mainPanel);

    }
}
