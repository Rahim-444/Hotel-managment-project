package main.java.com.example.Poo.view;

import main.java.com.example.Poo.controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private int screenHeight;
    private int screenWidth;
    private int buttonWidth;
    private int padding;

    private JTextField emailField;
    private JPasswordField passwordField;

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
        Button loginButton = new Button("Login");
        ImageIcon icon = new ImageIcon("src/main/java/resources/Login.png");
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(this.screenWidth / 2, this.screenHeight, Image.SCALE_SMOOTH);
        icon = new ImageIcon(resizedImage);
        JLabel picLabel = new JLabel(icon);
        picLabel.setBounds(0, 0, this.screenWidth / 2, this.screenHeight);
        this.emailField = new JTextField();
        // password input
        this.passwordField = new JPasswordField();
        this.emailField.setBounds((int) (0.75 * this.screenWidth) - (buttonWidth / 2), this.screenHeight
                - (this.screenHeight / 3) - 3 * this.padding, this.buttonWidth,
                50);
        this.passwordField.setBounds((int) (0.75 * this.screenWidth) - (buttonWidth / 2), this.screenHeight
                - (this.screenHeight / 3) - 2 * this.padding, this.buttonWidth,
                50);
        panel.setLayout(null);
        panel2.setLayout(null);
        panel.add(picLabel);
        panel2.setBackground(new Color(116, 105, 182));
        // panel.setBackground(new Color(255, 230, 230));
        panel.setBounds(0, 0, this.screenWidth / 2, this.screenHeight);
        panel2.setBounds(this.screenWidth / 2, 0, this.screenWidth / 2, this.screenHeight);
        loginButton.setBounds((int) (0.75 * this.screenWidth) - (buttonWidth / 2), this.screenHeight
                - (this.screenHeight / 3), this.buttonWidth,
                50);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get user input
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Pass input to controller for processing
                new UserController(Login.this).loginUser(email, password);
            }
        });
        panel2.add(loginButton);
        panel2.add(this.emailField);
        panel2.add(this.passwordField);
        add(panel);
        add(panel2);
        setVisible(true);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccessMessage() {
        JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

}
