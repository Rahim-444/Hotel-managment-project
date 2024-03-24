package main.java.com.example.Poo;

import javax.swing.*;
import main.java.com.example.Poo.view.*;

public class Application {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        new Runnable() {
          @Override
          public void run() {
            RoomManagementView view = new RoomManagementView(720, 1280);
            view.setVisible(true);
          }
        });
  }
}
