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
  private boolean isValidUser;
  private final Login loginView;

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

  private static boolean checkIfAlreadyLoggedin(String email) {
    String filePath = "user_credentials.txt"; // Path to your file
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(",");
        String storedEmail = parts[0].trim();
        if (storedEmail.trim().equals(email.trim())) {
          return true; // User already logged in
        }
      }
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Error reading logged in users file.", e);
    }
    return false; // User not logged in or error occurred
  }

  private static boolean checkUserCredentials(String email, String password) {
    String filePath = "user_credentials.txt"; // Path to your file
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = br.readLine()) != null) {
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
    if (checkIfAlreadyLoggedin(email)) {
      return false; // User already exists
    }

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
}

// package main.java.com.example.Poo.controller;

// import main.java.com.example.Poo.view.Login;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.logging.Level;
// import java.util.logging.Logger;
// import java.security.MessageDigest;
// import java.security.NoSuchAlgorithmException;
// import java.util.Base64;

// public class UserController {

//   private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
//   private boolean isValidUser;
//   private final Login loginView;

//   private final String dbUrl = "jdbc:postgresql://localhost:5432/mydatabase";
//   private final String dbUsername = "postgres";
//   private final String dbPassword = "postgres";

//   public UserController(Login loginView) {
//     this.loginView = loginView;
//   }

//   public void loginUser(String email, String password) {
//     if (email.isEmpty() || password.isEmpty()) {
//       loginView.showErrorMessage("Email and password are required");
//       return;
//     }
//     this.isValidUser = checkUserCredentials(email, password);

//     if (isValidUser) {
//       loginView.showSuccessMessage("User logged in successfully");
//     } else {
//       loginView.showErrorMessage("Invalid email or password");
//     }
//   }

//   public void createUser(String email, String password, String confirmedPassword) {
//     if (email.isEmpty() || password.isEmpty() || confirmedPassword.isEmpty()) {
//       loginView.showErrorMessage("Email, password and confirmed password are required");
//       return;
//     }
//     if (!password.equals(confirmedPassword)) {
//       loginView.showErrorMessage("Password and confirmed password do not match");
//       return;
//     }
//     boolean isRegistered = registerUser(email, password);
//     if (isRegistered) {
//       loginView.showSuccessMessage("User registered successfully");
//     } else {
//       loginView.showErrorMessage("Email already registered");
//     }
//   }

//   private boolean checkUserCredentials(String email, String password) {
//     String query = "SELECT * FROM users WHERE email = ? AND password = ?";
//     try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
//         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//       preparedStatement.setString(1, email);
//       preparedStatement.setString(2, hashPassword(password));
//       try (ResultSet resultSet = preparedStatement.executeQuery()) {
//         return resultSet.next();
//       }
//     } catch (SQLException e) {
//       LOGGER.log(Level.SEVERE, "Error checking user credentials", e);
//       return false;
//     }
//   }

//   private boolean registerUser(String email, String password) {
//     if (checkIfEmailExists(email)) {
//       return false; // Email already registered
//     }
//     String query = "INSERT INTO users (email, password) VALUES (?, ?)";
//     try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
//         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//       preparedStatement.setString(1, email);
//       preparedStatement.setString(2, hashPassword(password));
//       int rowsInserted = preparedStatement.executeUpdate();
//       return rowsInserted > 0;
//     } catch (SQLException e) {
//       LOGGER.log(Level.SEVERE, "Error registering user", e);
//       return false;
//     }
//   }

//   private boolean checkIfEmailExists(String email) {
//     String query = "SELECT * FROM users WHERE email = ?";
//     try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
//         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//       preparedStatement.setString(1, email);
//       try (ResultSet resultSet = preparedStatement.executeQuery()) {
//         return resultSet.next();
//       }
//     } catch (SQLException e) {
//       LOGGER.log(Level.SEVERE, "Error checking if email exists", e);
//       return false;
//     }
//   }

//   private String hashPassword(String password) {
//     try {
//       // Create a SHA-256 hash of the password
//       MessageDigest digest = MessageDigest.getInstance("SHA-256");
//       byte[] hashBytes = digest.digest(password.getBytes());
//       // Encode the hash bytes using Base64
//       return Base64.getEncoder().encodeToString(hashBytes);
//     } catch (NoSuchAlgorithmException e) {
//       LOGGER.log(Level.SEVERE, "Error hashing password", e);
//       return null; // Failed to hash password
//     }
//   }
// }
