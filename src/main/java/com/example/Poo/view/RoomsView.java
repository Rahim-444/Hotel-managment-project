package main.java.com.example.Poo.view;

import java.awt.*;
import javax.swing.*;
import main.java.com.example.Poo.model.Room;

public class RoomsView extends JFrame {
  private static final long serialVersionUID = 1L;
  private JPanel panel;
  private JLabel roomName;
  private JLabel roomDescription;
  private JButton joinButton;
  private JButton leaveButton;

  public RoomsView(Room room) {
    this.roomName = new JLabel(room.getType());
    this.roomDescription = new JLabel("room descreption");
    this.joinButton = new JButton("Check in");
    this.leaveButton = new JButton("Check out");
    joinButton.setSize(20, 10);
    leaveButton.setSize(40, 20);
    this.panel = new JPanel();
    this.panel.setLayout(new GridLayout(5, 1));
    this.panel.add(this.roomName);
    this.panel.add(this.roomDescription);
    this.panel.add(this.joinButton);
    this.panel.add(this.leaveButton);
    this.add(this.panel);
    this.setSize(800, 600);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }
}
