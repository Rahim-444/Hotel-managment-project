package main.java.com.example.Poo;

import javax.swing.*;
import main.java.com.example.Poo.controller.*;
import main.java.com.example.Poo.view.*;

public class Application {
  public static void main(String[] args) {
    Login loginView = new Login(720, 1280, true);
    loginView.setVisible(true);
    // RoomManagementView roomManagementView =
    // new RoomManagementView(720, 1280, new RoomManagementController());
    // roomManagementView.setVisible(true);
    AdminReservationsView adminReservationsView = new AdminReservationsView(720, 1280);
    adminReservationsView.setVisible(true);
  }
}
