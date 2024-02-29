package main.java.com.example.Poo.view;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    public int screenHeight;
    public int screenWidth;
    public int buttonWidth;
    public int padding;

    public Login(int screenHeight, int screenWidth) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.buttonWidth = screenWidth / 3;
        this.padding = screenHeight / 10;
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        ImageIcon icon = new ImageIcon(
                "./Mobile login pana.png");
        Button button = new Button("Login");
        JTextField email = new JTextField();
        // password input
        JPasswordField password = new JPasswordField();
        email.setBounds((int) (0.75 * this.screenWidth) - (buttonWidth / 2), this.screenHeight
                - (this.screenHeight / 3) - 3 * this.padding, this.buttonWidth,
                50);
        password.setBounds((int) (0.75 * this.screenWidth) - (buttonWidth / 2), this.screenHeight
                - (this.screenHeight / 3) - 2 * this.padding, this.buttonWidth,
                50);
        panel.setLayout(null);
        panel2.setLayout(null);
        panel2.setBackground(new Color(116, 105, 182));
        panel.setBackground(new Color(255, 230, 230));
        panel.setBounds(0, 0, this.screenWidth / 2, this.screenHeight);
        panel2.setBounds(this.screenWidth / 2, 0, this.screenWidth / 2, this.screenHeight);
        button.setBounds((int) (0.75 * this.screenWidth) - (buttonWidth / 2), this.screenHeight
                - (this.screenHeight / 3), this.buttonWidth,
                50);
        panel2.add(button);
        panel.add(new JLabel(icon));
        panel2.add(email);
        panel2.add(password);
        add(panel);
        add(panel2);
        setVisible(true);

    }
}
