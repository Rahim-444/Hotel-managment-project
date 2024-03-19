package main.java.com.example.Poo.view;

import java.awt.*;
import javax.swing.*;

public class HotelsView extends JFrame {
  private static final long serialVersionUID = 1L;
  private JPanel panel;
  private JLabel label;
  private JButton button;

  public HotelsView(int height, int width, boolean visible) {
    setTitle("Hotels");
    setSize(width, height);
    setLocationRelativeTo(null);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    panel = new JPanel();
    label = new JLabel("Hotels");
    button = new JButton("Back");
    panel.add(label);
    panel.add(button);
    add(panel, BorderLayout.CENTER);
    for (int i = 0; i < 10; i++) {
      panel.add(new HotelCard());
    }
    setVisible(visible);
  }
}
