package main.java.com.example.Poo.view;

import java.awt.*;
import java.util.concurrent.TimeUnit;
import javax.mail.*;
import javax.swing.*;
import main.java.com.example.Poo.controller.*;

public class ForgotPassword {

  private static final long expirationTime = 10; // Token expiration time in minutes
  private static final java.util.Map<String, Long> tokens = new java.util.HashMap<>();
  private static SendEmail sendEmail =
      new SendEmail(); // Assuming SendEmail is a class you've defined

  public ForgotPassword(String email) {
    int res = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset your password?");
    if (res == JOptionPane.YES_OPTION) {
      boolean check = UserController.checkIfEmailExists(email);
      if (!check) {
        JOptionPane.showMessageDialog(null, "Email does not exist");
        return;
      }

      String token = TokenGenerator.generateToken();
      tokens.put(token, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expirationTime));
      if (email.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Email field is empty");
        return;
      }
      String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
      if (!email.matches(regex)) {
        JOptionPane.showMessageDialog(null, "Invalid email format");
        return;
      }

      try {
        sendEmail.setupServerProperties();
        sendEmail.draftEmail(email, "Verification Token", "Your verification token is: " + token);
        boolean emailSent = sendEmail.sendEmail(email);
        if (emailSent) {
          String ver =
              JOptionPane.showInputDialog("Enter the verification code sent to your email");
          if (ver == null) {
            return;
          }
          if (ver.equals(token)) {
            UserController.alterUserPassword(email);
          } else {
            JOptionPane.showMessageDialog(
                null, "Invalid verification code", "Error", JOptionPane.ERROR_MESSAGE);
            return;
          }
        } else {
          JOptionPane.showMessageDialog(null, "Email not sent", "Error", JOptionPane.ERROR_MESSAGE);
          return;
        }

      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}
