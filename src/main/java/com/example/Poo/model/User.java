package main.java.com.example.Poo.model;

public class User {
  private String email;
  private String password;
  private boolean isAdmin;
  private int userID;

  public User(int userID, String email, String password, boolean isAdmin) {
    this.email = email;
    this.password = password;
    this.isAdmin = isAdmin;
    this.userID = userID;
  }

  public User(String email, String password, boolean isAdmin) {
    this.email = email;
    this.password = password;
    this.isAdmin = isAdmin;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }
}
