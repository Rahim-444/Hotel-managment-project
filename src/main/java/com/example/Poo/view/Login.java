package main.java.com.example.Poo.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.*;
import main.java.com.example.Poo.controller.UserController;
import main.java.com.example.Poo.model.User;

public class Login extends JFrame implements ComponentListener {
        private int screenWidth;
        private int screenHeight;
        private boolean isLogin;
        private int buttonWidth;
        private int padding;
        private JPanel panel = new JPanel();
        private JPanel panel2 = new JPanel();
        private JButton loginButton;
        private JLabel confirmLabel;
        private JPasswordField confirmPasswordField;
        private JLabel switchContent;
        private JLabel switchClickabale;
        private JLabel forgotPassword;
        JLabel label;
        private ImageIcon icon;
        private ImageIcon logo = new ImageIcon("src/main/java/resources/Sheraton background removed.png");
        private JTextField emailField;
        private JPasswordField passwordField;
        Timer resizeTimer = new Timer(
                        200,
                        new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                        screenHeight = getHeight();
                                        screenWidth = getWidth();
                                        drawElements(screenWidth, screenHeight, isLogin);
                                }
                        });

        public Login(int screenHeight, int screenWidth, boolean isLogin) {
                setMinimumSize(new Dimension(800, 600));
                this.screenHeight = screenHeight;
                this.screenWidth = screenWidth;
                this.buttonWidth = screenWidth / 3;
                this.padding = screenHeight / 10;
                this.isLogin = isLogin;
                drawElements(screenWidth, screenHeight, isLogin);
        }

        public void drawElements(int screenWidth, int screenHeight, boolean isLogin) {
                this.getContentPane().removeAll();
                panel.removeAll();
                panel2.removeAll();
                this.screenHeight = screenHeight;
                this.screenWidth = screenWidth;
                this.buttonWidth = screenWidth / 3;
                this.padding = screenHeight / 10;
                this.isLogin = isLogin;
                if (isLogin) {
                        setTitle("Login");
                        this.loginButton = new JButton("Login");
                        this.icon = new ImageIcon("src/main/java/resources/Login.png");
                        this.label = new JLabel("Login to your account");
                        loginButton.setBounds(
                                        (int) (0.75 * this.screenWidth) - (buttonWidth / 2),
                                        this.screenHeight - (this.screenHeight / 3),
                                        this.buttonWidth,
                                        50);
                        this.switchContent = new JLabel("Don't have an account? ");
                        this.switchClickabale = new JLabel("Sign Up");
                        this.switchClickabale.setBounds(
                                        (int) (0.75 * this.screenWidth) + 56,
                                        (int) (this.screenHeight - (this.screenHeight / 3) + 0.6 * this.padding),
                                        this.buttonWidth,
                                        50);
                        this.switchContent.setBounds(
                                        (int) (0.75 * this.screenWidth) - 100,
                                        (int) (this.screenHeight - (this.screenHeight / 3) + 0.6 * this.padding),
                                        this.buttonWidth,
                                        50);
                        this.switchClickabale.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        this.switchClickabale.addMouseListener(
                                        new java.awt.event.MouseAdapter() {
                                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                        new Login(screenHeight, screenWidth, false).setVisible(true);
                                                        dispose();
                                                }
                                        });
                        this.forgotPassword = new JLabel("Forgot Password?");
                        this.forgotPassword.setBounds(
                                        (int) (0.75 * this.screenWidth) - 50,
                                        (int) (this.screenHeight - (this.screenHeight / 3) + 1.2 * this.padding),
                                        this.buttonWidth,
                                        50);
                        this.forgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        this.forgotPassword.addMouseListener(
                                        new java.awt.event.MouseAdapter() {
                                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                        new ForgotPassword();
                                                        dispose();
                                                }
                                        });
                        panel2.add(forgotPassword);
                } else {
                        setTitle("Sign Up");
                        this.loginButton = new JButton("Sign Up");
                        this.label = new JLabel("Sign up for an account");
                        this.icon = new ImageIcon("src/main/java/resources/SignUp.png");
                        this.confirmPasswordField = new JPasswordField();
                        this.confirmLabel = new JLabel("Confirm Password");
                        this.confirmLabel.setBounds(
                                        (int) (0.75 * this.screenWidth) - (buttonWidth / 2),
                                        (int) (this.screenHeight - (this.screenHeight / 3) - 1.3 * this.padding),
                                        this.buttonWidth,
                                        50);
                        this.confirmPasswordField.setBounds(
                                        (int) (0.75 * this.screenWidth) - (buttonWidth / 2),
                                        (int) (this.screenHeight - (this.screenHeight / 3) - 0.8 * this.padding),
                                        this.buttonWidth,
                                        50);
                        loginButton.setBounds(
                                        (int) (0.75 * this.screenWidth) - (buttonWidth / 2),
                                        this.screenHeight - (this.screenHeight / 4),
                                        this.buttonWidth,
                                        50);
                        this.switchContent = new JLabel("Already have an account? ");
                        this.switchClickabale = new JLabel("Login");
                        this.switchClickabale.setBounds(
                                        (int) (0.75 * this.screenWidth) + 65,
                                        (int) (this.screenHeight - (this.screenHeight / 4) + 0.6 * this.padding),
                                        this.buttonWidth,
                                        50);
                        this.switchContent.setBounds(
                                        (int) (0.75 * this.screenWidth) - 100,
                                        (int) (this.screenHeight - (this.screenHeight / 4) + 0.6 * this.padding),
                                        this.buttonWidth,
                                        50);
                        panel2.add(confirmLabel);
                        panel2.add(confirmPasswordField);
                        this.switchClickabale.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        this.switchClickabale.addMouseListener(
                                        new java.awt.event.MouseAdapter() {

                                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                        new Login(screenHeight, screenWidth, true).setVisible(true);
                                                        dispose();
                                                }
                                        });
                }
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(screenWidth, screenHeight);
                setLocationRelativeTo(null);
                Image image = icon.getImage();
                Image logoImage = logo.getImage();
                Image resizedLogo = logoImage.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
                Image resizedImage = image.getScaledInstance(this.screenWidth / 2, this.screenHeight,
                                Image.SCALE_SMOOTH);
                icon = new ImageIcon(resizedImage);
                logo = new ImageIcon(resizedLogo);
                JLabel picLabel = new JLabel(icon);
                JLabel logoLabel = new JLabel(logo);
                logoLabel.setBounds(
                                (int) ((0.75 * this.screenWidth) - 150),
                                (int) (this.screenHeight - (this.screenHeight / 3) - 6.5 * this.padding),
                                300,
                                200);
                picLabel.setBounds(0, 0, this.screenWidth / 2, this.screenHeight);
                this.switchClickabale.setFont(new Font("Arial", Font.BOLD, 14));
                this.switchClickabale.setForeground(new Color(191, 155, 48));
                this.emailField = new JTextField();
                this.passwordField = new JPasswordField();
                this.emailField.setBounds(
                                (int) (0.75 * this.screenWidth) - (buttonWidth / 2),
                                this.screenHeight - (this.screenHeight / 3) - 3 * this.padding,
                                this.buttonWidth,
                                50);
                JLabel emailLabel = new JLabel("Email");
                emailLabel.setBounds(
                                (int) (0.75 * this.screenWidth) - (buttonWidth / 2),
                                (int) (this.screenHeight - (this.screenHeight / 3) - 3.5 * this.padding),
                                this.buttonWidth,
                                50);
                this.passwordField.setBounds(
                                (int) (0.75 * this.screenWidth) - (buttonWidth / 2),
                                (int) (this.screenHeight - (this.screenHeight / 3) - 1.9 * this.padding),
                                this.buttonWidth,
                                50);
                JLabel passwordLabel = new JLabel("Password");
                passwordLabel.setBounds(
                                (int) (0.75 * this.screenWidth) - (buttonWidth / 2),
                                (int) (this.screenHeight - (this.screenHeight / 3) - 2.4 * this.padding),
                                this.buttonWidth,
                                50);
                panel.setLayout(null);
                panel2.setLayout(null);
                panel.add(picLabel);
                panel2.add(logoLabel);
                panel.setBackground(new Color(191, 155, 48));
                panel2.setBackground(new Color(220, 220, 220));
                panel.setBounds(0, 0, this.screenWidth / 2, this.screenHeight);
                label.setFont(new Font("Arial", Font.BOLD, 20));
                label.setBounds(
                                (int) (0.75 * this.screenWidth) - 100,
                                this.screenHeight - (this.screenHeight / 3) - 4 * this.padding,
                                this.buttonWidth,
                                50);
                panel2.add(label);
                loginButton.addActionListener(
                                new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                                String email = emailField.getText();
                                                boolean isAdmin = false;
                                                if (email.contains("@hotel.com")) {
                                                        isAdmin = true;
                                                }
                                                String password = new String(passwordField.getPassword());
                                                if (isLogin) {
                                                        new UserController(Login.this)
                                                                        .loginUser(new User(email, password, isAdmin));
                                                        return;
                                                } else {
                                                        String confirmedPassword = new String(
                                                                        confirmPasswordField.getPassword());
                                                        new UserController(Login.this).createUser(email, password,
                                                                        confirmedPassword);
                                                }
                                        }
                                });
                panel2.add(loginButton);
                panel2.add(emailLabel);
                panel2.add(passwordLabel);
                panel2.add(switchClickabale);
                panel2.add(switchContent);
                panel2.add(this.emailField);
                panel2.add(this.passwordField);
                add(panel);
                add(panel2);
                panel2.revalidate();
                panel.revalidate();
                panel2.paintImmediately(0, 0, panel2.getWidth(), panel2.getHeight());
                panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight());

                resizeTimer.setRepeats(false); // Make sure the timer
                // only runs once
                this.addComponentListener(this);
                setVisible(true);
        }

        @Override
        public void componentResized(ComponentEvent e) {
                resizeTimer.restart();
        }

        @Override
        public void componentMoved(ComponentEvent e) {
        }

        @Override
        public void componentShown(ComponentEvent e) {
        }

        @Override
        public void componentHidden(ComponentEvent e) {
        }

        public void showErrorMessage(String message) {
                JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }

        public void showSuccessMessage(String message) {
                JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
        }
}
