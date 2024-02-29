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
        panel.setLayout(null);
        panel.setBackground(new Color(255, 230, 230));
        panel.setBounds(0, 0, screenWidth, screenHeight);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds((int) (0.75 * screenWidth) - (buttonWidth / 2), screenHeight
                - (screenHeight / 3) - 3 * padding, buttonWidth,
                50);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds((int) (0.75 * screenWidth) - (buttonWidth / 2), screenHeight
                - (screenHeight / 3) - 2 * padding, buttonWidth,
                50);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds((int) (0.75 * screenWidth) - (buttonWidth / 2), screenHeight
                - (screenHeight / 3) - padding, buttonWidth,
                50);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds((int) (0.75 * screenWidth) - (buttonWidth / 2), screenHeight
                - (screenHeight / 3), buttonWidth,
                50);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds((int) (0.75 * screenWidth) - (buttonWidth / 2), screenHeight
                - (screenHeight / 3) + padding, buttonWidth,
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
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccessMessage() {
        JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login(600, 800));
    }
}

