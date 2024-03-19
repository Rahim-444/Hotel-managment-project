package main.java.com.example.Poo.controller;

import main.java.com.example.Poo.view.Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class UserController {

  private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
  private boolean isValidUser;
  private final Login loginView;

  private final String dbUrl = "jdbc:postgresql://localhost:5432/mydatabase";
  private final String dbUsername = "postgres";
  private final String dbPassword = "    ";

  public UserController(Login loginView) {
    this.loginView = loginView;
  }

  public void loginUser(String email, String password) {
    if (email.isEmpty() || password.isEmpty()) {
      loginView.showErrorMessage("Email and password are required");
      return;
    }
    this.isValidUser = checkUserCredentials(email, password);

    if (isValidUser) {
      loginView.showSuccessMessage("User logged in successfully");
    } else {
      loginView.showErrorMessage("Invalid email or password");
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

  public void createUser(String email, String password, String confirmedPassword) {
    if (email.isEmpty() || password.isEmpty() || confirmedPassword.isEmpty()) {
      loginView.showErrorMessage("Email, password and confirmed password are required");
      return;
    }
    if (!password.equals(confirmedPassword)) {
      loginView.showErrorMessage("Password and confirmed password do not match");
      return;
    }
    boolean isRegistered = registerUser(email, password);
    if (isRegistered) {
      loginView.showSuccessMessage("User registered successfully");
    } else {
      loginView.showErrorMessage("Email already registered");
    }
  }

  private boolean checkUserCredentials(String email, String password) {
    String query = "SELECT * FROM users WHERE email = ? AND password = ?";
    try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername,
        dbPassword);
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, hashPassword(password));
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        return resultSet.next();
      }
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Error checking user credentials", e);
      return false;
    }
  }

  private boolean checkIfUsersTableExists() {
    // SQL query to check if the users table exists
    String query = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'users')";
    try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      // Check if the query returned a result
      if (resultSet.next()) {
        // Return true if the result indicates that the table exists, false otherwise
        return resultSet.getBoolean(1);
      }
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Error checking if users table exists", e);
    }
    // Return false if an error occurred or if the result set was empty
    return false;
  }

  private void createUsersTable() {
    // SQL statement to create the users table
    String query = "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, email VARCHAR(255) UNIQUE, password VARCHAR(255))";
    try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      // Execute the SQL statement to create the users table
      preparedStatement.executeUpdate();
      System.out.println("users table created successfully");
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Error creating users table", e);
    }
  }

  private boolean registerUser(String email, String password) {
    if (checkIfEmailExists(email)) {
      return false; // Email already registered
    }
    if (!checkIfUsersTableExists()) {
      createUsersTable();
    }
    String query = "INSERT INTO users (email, password) VALUES (?, ?)";
    try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername,
        dbPassword);
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, hashPassword(password));
      int rowsInserted = preparedStatement.executeUpdate();
      return rowsInserted > 0;
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Error registering user", e);
      return false;
    }
  }

  private boolean checkIfEmailExists(String email) {
    String query = "SELECT * FROM users WHERE email = ?";
    try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername,
        dbPassword);
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setString(1, email);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        return resultSet.next();
      }
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Error checking if email exists", e);
      return false;
    }
  }
}
