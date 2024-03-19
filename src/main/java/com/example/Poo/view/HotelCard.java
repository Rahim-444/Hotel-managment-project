package main.java.com.example.Poo.view;

import java.awt.*;
import javax.swing.*;

public class HotelCard extends JPanel {
  private static final long serialVersionUID = 1L;
  private JLabel label;
  private JButton button;

  public HotelCard() {
    setLayout(new BorderLayout());
    label = new JLabel("Hotels");
    button = new JButton("Back");
    add(label, BorderLayout.NORTH);
    add(button, BorderLayout.SOUTH);
  }
}
