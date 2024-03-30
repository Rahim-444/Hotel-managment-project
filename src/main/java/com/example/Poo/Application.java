package main.java.com.example.Poo;

import javax.swing.*;
import main.java.com.example.Poo.controller.RoomManagementController;
import main.java.com.example.Poo.model.Room;
import main.java.com.example.Poo.view.*;

public class Application {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        new Runnable() {
          @Override
          public void run() {
            Room room = new Room(11, "hello", true, 112);
            RoomManagementController controller = new RoomManagementController(room);
            RoomManagementView view = new RoomManagementView(720, 1280, controller);
            view.setVisible(true);
          }
        });
  }
}
