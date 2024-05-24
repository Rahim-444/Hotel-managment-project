package main.java.com.example.Poo.model;

public class Database {
  static String url =
      "jdbc:postgresql://pg-337a9f5c-danaamine080807.j.aivencloud.com:20758/defaultdb";
  static String user = "avnadmin";
  static String password = "AVNS_IpwBPJ7uvfZvldNmFWE";

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
