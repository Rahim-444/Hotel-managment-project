package main.java.com.example.Poo.model;

public class User {
  private String email;
  private String password;
  private boolean isAdmin;

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
}
