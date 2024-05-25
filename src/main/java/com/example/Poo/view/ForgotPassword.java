package main.java.com.example.Poo.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import javax.mail.*;
import javax.swing.*;
import main.java.com.example.Poo.controller.*;

public class ForgotPassword {

  private static final long expirationTime = 10; // Token expiration time in minutes
  private static final java.util.Map<String, Long> tokens = new java.util.HashMap<>();
  private static String userEmail;
  private static JTextField EmailField = new JTextField(20);
  private static JPanel EmailInputPanel = new JPanel();
  private static JPanel VerificationCodeInputPanel = new JPanel();
  // private static Message msg = new Message(); // Assuming Message is a class
  // you've defined
  private static SendEmail sendEmail =
      new SendEmail(); // Assuming SendEmail is a class you've defined

  public ForgotPassword() {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 300);
    frame.setLayout(new BorderLayout());

    EmailInputPanel.setLayout(new GridLayout(2, 1));
    EmailInputPanel.add(new JLabel("Enter your email:"));
    EmailInputPanel.add(EmailField);
    frame.add(EmailInputPanel, BorderLayout.CENTER);

    JButton nextButton = new JButton("Next");
    frame.add(nextButton, BorderLayout.SOUTH);

    nextButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String email = EmailField.getText();
            userEmail = email;
            if (email.isEmpty()) {
              JOptionPane.showMessageDialog(frame, "Email field is empty");
              return;
            }
            String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
            if (!email.matches(regex)) {
              // msg.displayMessage(Message.MessageType.ERROR, "Invalid email format",
              // EmailInputPanel, frame.getLayout());
              return;
            }
            // Document loginUser = Database.findInDataBase("Guests", "email", email);
            // if (loginUser == null) {
            // msg.displayMessage(Message.MessageType.ERROR, "Email does not exist",
            // EmailInputPanel, frame.getLayout());
            // return;
            // }

            String token = TokenGenerator.generateToken();
            tokens.put(
                token, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expirationTime));
            email = "abderrahimbelkacemi33@gmail.com";

            try {
              sendEmail.setupServerProperties();
              sendEmail.draftEmail(
                  email, "Verification Token", "Your verification token is: " + token);
              boolean emailSent = sendEmail.sendEmail(email);
              if (emailSent) {
                // msg.displayMessage(
                // Message.MessageType.SUCCESS,
                // "Email sent successfully",
                // EmailInputPanel,
                // frame.getLayout());
              } else {
                // msg.displayMessage(
                // Message.MessageType.ERROR,
                // "Email has not been sent, try again",
                // EmailInputPanel,
                // frame.getLayout());
                return;
              }

            } catch (Exception ex) {
              ex.printStackTrace();
            }

            frame.remove(EmailInputPanel);
            frame.add(VerificationCodeInputPanel);
            frame.revalidate();
            frame.repaint();
          }
        });

    frame.setVisible(true);
  }
}
