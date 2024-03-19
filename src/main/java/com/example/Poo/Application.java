package main.java.com.example.Poo;

import main.java.com.example.Poo.view.*;

public class Application {
  public static void main(String[] args) {
    // Create and display the login view
    Login login = new Login(720, 1280, true);
    login.setVisible(true);
    // HotelsView hotels = new HotelsView(720, 1280, true);
    // hotels.setVisible(true);
  }
}
