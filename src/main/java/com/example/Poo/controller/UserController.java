package main.java.com.example.Poo.controller;

import main.java.com.example.Poo.view.Login;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class UserController {

  private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
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

  private static boolean checkUserCredentials(String email, String password) {
    String filePath = "user_credentials.txt"; // Path to your file
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
     String line;
      while ((line = br.readLine()) != null) {
        // Assuming each line in the file contains email and password separated by a
        // comma
        String[] parts = line.split(",");
        if (parts.length >= 2) { // Check if there are at least two elements in the array
          String storedEmail = parts[0].trim();
          String storedPassword = parts[1].trim();
          email = email.trim();
          if (email.equals(storedEmail) && hashPassword(password).equals(storedPassword)) {
            return true; // Credentials match
          }
        } else {
          // Handle case where line doesn't contain email and password separated by a
          // comma
          LOGGER.log(Level.WARNING, "Invalid format in user credentials file: " + line);
        }
      }
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Error reading user credentials file.", e);
    }
    return false; // Credentials not found or error occurred
  }

  private static boolean registerUser(String email, String password) {

    // Hash the password
    String hashedPassword = hashPassword(password);
    if (hashedPassword == null) {
      return false; // Failed to hash password
    }

    // Store the user data in a file (append to the end)
    String filePath = "user_credentials.txt";
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
      // Write user data to file (email and hashed password separated by a comma)
      bw.write(email + "," + hashedPassword);
      bw.newLine();
      return true; // User registered successfully
    } catch (IOException e) {
      e.printStackTrace();
      return false; // Failed to register user
    }
  }

  private static String hashPassword(String password) {
    try {
      // Create a SHA-256 hash of the password
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hashBytes = digest.digest(password.getBytes());
      // Encode the hash bytes using Base64
      return Base64.getEncoder().encodeToString(hashBytes);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null; // Failed to hash password
    }
  }

  // You can add more methods here for handling other user-related functionality
}
