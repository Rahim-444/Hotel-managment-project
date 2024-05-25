package main.java.com.example.Poo;

import javax.swing.*;
import main.java.com.example.Poo.controller.*;
import main.java.com.example.Poo.view.*;

public class Application {
  public static void main(String[] args) {
    // Login loginView = new Login(720, 1280, true);
    // loginView.setVisible(true);
    // RoomManagementView roomManagementView =
    // new RoomManagementView(720, 1280, new RoomManagementController());
    // roomManagementView.setVisible(true);
    // AdminReservationsView adminReservationsView = new AdminReservationsView(720,
    // 1280);
    // adminReservationsView.setVisible(true);
    // String input = JOptionPane.showInputDialog("Enter your email");

    String token = TokenGenerator.generateToken();
    // tokens.put(token, System.currentTimeMillis() +
    // TimeUnit.MINUTES.toMillis(expirationTime));

    SendEmail sendEmail = new SendEmail();

    try {
      sendEmail.setupServerProperties();
      sendEmail.draftEmail(
          "abderrahimbelkacemi33@gmail.com",
          "this is a private token , dont share it  , \n",
          "Your verification token is: " + token);
      boolean emailSent = sendEmail.sendEmail("abderrahimbelkacemi33@gmail.com");
      if (emailSent) {
        // msg.displayMessage(Message.MessageType.SUCCESS, "email sent successfully ",
        // panel, layout);
        System.out.println("email sent successfully");
      } else {
        // msg.displayMessage(Message.MessageType.ERROR, "email has been sent , try
        // again", panel, layout);
        System.out.println("email not sent successfully");
        return;
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }

    // remove(EmailInputPanel);
    // add(VerificationCodeInputPanel);
    // revalidate();
    // repaint();
  }
}
