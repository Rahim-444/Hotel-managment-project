package main.java.com.example.Poo;

import main.java.com.example.Poo.view.Login;

public class Application {
    public static void main(String[] args) {
        // Create and display the login view
        Login login = new Login(600, 800);
        login.setVisible(true);
    }
}
