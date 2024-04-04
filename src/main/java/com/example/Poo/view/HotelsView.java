package main.java.com.example.Poo.view;

import java.awt.*;
import javax.swing.*;

public class HotelsView extends JFrame {
  private JPanel panel;
  private JLabel label;
  private JButton button;

  public HotelsView(int height, int width) {
    setTitle("Hotels");
    setSize(width, height);
    setLocationRelativeTo(null);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    panel =
        new JPanel(
            new GridLayout(
                0, 2, 10, 10)); // GridLayout with 2 columns and 10px horizontal and vertical gap
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adding some padding
    label = new JLabel("Hotels");
    label.setFont(new Font("Arial", Font.BOLD, 24)); // Example of customizing font
    button = new JButton("Back");

    add(label, BorderLayout.NORTH); // Add label to the top
    add(button, BorderLayout.SOUTH); // Add button to the bottom
    add(
        new JScrollPane(panel),
        BorderLayout.CENTER); // Add panel with cards in the center with scrollbars if necessary

    for (int i = 0; i < 10; i++) {
      panel.add(new HotelCard());
    }

    setVisible(true); // Show the frame
  }

  private class HotelCard extends JPanel {
    public HotelCard() {
      setPreferredSize(new Dimension(200, 150)); // Example size for the card
      setBackground(Color.white); // Example background color
      setBorder(BorderFactory.createLineBorder(Color.gray, 1)); // Example border
      setLayout(new BorderLayout());

      JLabel nameLabel = new JLabel("Hotel Name"); // Example label for hotel name
      nameLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text

      JLabel priceLabel = new JLabel("$100 per night"); // Example label for price
      priceLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text

      add(nameLabel, BorderLayout.NORTH);
      add(
          new JLabel(new ImageIcon("hotel_image.jpg")),
          BorderLayout.CENTER); // Example image for the hotel
      add(priceLabel, BorderLayout.SOUTH);
    }
  }
}
