package main.java.com.example.Poo.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.com.example.Poo.model.Database;
import main.java.com.example.Poo.model.User;
import main.java.com.example.Poo.view.*;
import main.java.com.example.Poo.view.Login;

public class UserController {

  private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
  private final Login loginView;

  public UserController(Login loginView) {
    this.loginView = loginView;
  }

  public void loginUser(User user) {
    if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
      loginView.showErrorMessage("Email and password are required");
      return;
    }
    int userId = checkUserCredentials(user.getEmail(), user.getPassword());

    if (userId != -1) {
      if (user.isAdmin()) {
        // close the login view and open the hotel management view
        loginView.dispose();
        RoomManagementController controller = new RoomManagementController();
        RoomManagementView roomManagementView = new RoomManagementView(720, 1280, controller);
        roomManagementView.setVisible(true);
      } else {
        loginView.dispose();
        user.setUserID(userId);
        HotelsView hotelsView = new HotelsView(720, 1280, new RoomManagementController(), user);
        hotelsView.setVisible(true);
      }
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
    boolean isAdmin = email.contains("@hotel.com");
    boolean isRegistered = registerUser(email, password, isAdmin);
    if (isRegistered) {
      loginView.showSuccessMessage("User registered successfully");
    } else {
      loginView.showErrorMessage("Email already registered");
    }
  }

  private int checkUserCredentials(String email, String password) {
    String query = "SELECT * FROM users WHERE email = ? AND password = ?";
    try (Connection connection =
            DriverManager.getConnection(
                Database.getUrl(), Database.getUser(), Database.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, hashPassword(password));
      // try (ResultSet resultSet = preparedStatement.executeQuery()) {
      // return resultSet.next();
      // }
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getInt("id");
        } else {
          return -1;
        }
      }
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Error checking user credentials", e);
      return -1;
    }
  }

  private void createUsersTable() {
    // SQL statement to create the users table
    String query =
        "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, email VARCHAR(255) UNIQUE,"
            + " is_admin BOOLEAN DEFAULT FALSE, password VARCHAR(255))";

    try (Connection connection =
            DriverManager.getConnection(
                Database.getUrl(), Database.getUser(), Database.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      // Execute the SQL statement to create the users table
      preparedStatement.executeUpdate();
      System.out.println("users table created successfully");
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Error creating users table", e);
    }
  }

  private boolean registerUser(String email, String password, boolean isAdmin) {
    if (checkIfEmailExists(email)) {
      return false; // Email already registered
    }
    // crate if not exist users table
    createUsersTable();
    String query = "INSERT INTO users (id,email, password, is_admin) VALUES (? ,? , ?, ?)";
    try (Connection connection =
            DriverManager.getConnection(
                Database.getUrl(), Database.getUser(), Database.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setInt(1, (int) (System.currentTimeMillis() / 1000));
      preparedStatement.setString(2, email);
      preparedStatement.setString(3, hashPassword(password));
      preparedStatement.setBoolean(4, isAdmin);
      int rowsInserted = preparedStatement.executeUpdate();
      return rowsInserted > 0;
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Error registering user", e);
      return false;
    }
  }

  private boolean checkIfEmailExists(String email) {
    String query = "SELECT * FROM users WHERE email = ?";
    try (Connection connection =
            DriverManager.getConnection(
                Database.getUrl(), Database.getUser(), Database.getPassword());
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
