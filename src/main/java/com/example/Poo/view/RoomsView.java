package main.java.com.example.Poo.view;

import java.awt.*;
import javax.swing.*;

public class RoomsView extends JFrame {
  private static final long serialVersionUID = 1L;
  private JPanel panel;
  private JLabel roomName;
  private JLabel roomDescription;
  private JButton joinButton;
  private JButton leaveButton;

  public RoomsView(int screenHeight, int screenWidth, boolean isLogin) {
    this.roomName = new JLabel("Room Name");
    this.roomDescription = new JLabel("Room Description");
    this.joinButton = new JButton("Join");
    this.leaveButton = new JButton("Leave");

    this.panel = new JPanel();
    this.panel.setLayout(new GridLayout(3, 1));
    this.panel.add(this.roomName);
    this.panel.add(this.roomDescription);
    this.panel.add(this.joinButton);
    this.panel.add(this.leaveButton);

    this.add(this.panel);
    this.setSize(200, 200);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
}
