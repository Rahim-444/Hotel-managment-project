package main.java.com.example.Poo.controller;

import main.java.com.example.Poo.view.Login;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserController {
    private final Login loginView;

    public UserController(Login loginView) {
        this.loginView = loginView;
    }

    public void loginUser(String email, String password) {
        // Perform login logic here
        // For demonstration purposes, let's read user credentials from a file

        boolean isValidUser = checkUserCredentials(email, password);

        if (isValidUser) {
            loginView.showSuccessMessage();
        } else {
            loginView.showErrorMessage("Invalid email or password");
        }
    }

    private boolean checkUserCredentials(String email, String password) {
        String filePath = "user_credentials.txt"; // Path to your file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Assuming each line in the file contains email and password separated by a
                // comma
                String[] parts = line.split(",");
                String storedEmail = parts[0].trim();
                String storedPassword = parts[1].trim();
                email = email.trim();
                if (email.equals(storedEmail) && password.equals(storedPassword)) {
                    return true; // Credentials match
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Credentials not found or error occurred
    }

    // You can add more methods here for handling other user-related functionality
}
