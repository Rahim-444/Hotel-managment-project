package main.java.com.example.Poo.model;

public class Database {
  static String url = System.getenv("DbHost");
  static String user = System.getenv("DbUser");
  static String password = System.getenv("DbPassword");

  public static String getUrl() {
    return url;
  }

  public static String getUser() {
    return user;
  }

  public static String getPassword() {
    return password;
  }
}
