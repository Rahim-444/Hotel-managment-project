package main.java.com.example.Poo.controller;

import main.java.com.example.Poo.view.Login;

public class UserController {
    private final Login loginView;

    public UserController(Login loginView) {
        this.loginView = loginView;
    }

    public void loginUser(String email, String password) {
        // Perform login logic here
        // For demonstration purposes, let's just show a message based on the provided
        // email and password
        if ("example@example.com".equals(email) && "password".equals(password)) {
            loginView.showSuccessMessage();
        } else {
            loginView.showErrorMessage("Invalid email or password");
        }
    }

    // You can add more methods here for handling other user-related functionality
}
