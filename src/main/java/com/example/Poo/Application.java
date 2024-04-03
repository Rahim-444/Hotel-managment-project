package main.java.com.example.Poo;

import javax.swing.*;
import main.java.com.example.Poo.view.*;

public class Application {
  public static void main(String[] args) {
    Login loginView = new Login(720, 1280, true);
    loginView.setVisible(true);
  }
}
