package main.java.com.example.Poo.view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import main.java.com.example.Poo.controller.RoomManagementController;
import main.java.com.example.Poo.model.Room;

public class HotelsView extends JFrame {
  private JPanel panel;
  private JLabel label;
  private JButton button;
  private RoomManagementController controller;
  private List<Room> rooms;

  public HotelsView(int height, int width, RoomManagementController controller) {
    this.controller = controller;
    this.rooms = controller.getAllRooms();
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

    for (Room room : rooms) {
      panel.add(new HotelCard(room));
    }

    setVisible(true); // Show the frame
  }

  private class HotelCard extends JPanel {
    public HotelCard(Room room) {
      setPreferredSize(new Dimension(200, 150)); // Example size for the card
      setBackground(Color.white); // Example background color
      setBorder(BorderFactory.createLineBorder(Color.gray, 1)); // Example border
      setLayout(new BorderLayout());

      JLabel nameLabel = new JLabel(room.getType()); // Example label for hotel name
      nameLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text

      JLabel priceLabel = new JLabel("$" + room.getPricePerNight());
      priceLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text

      add(nameLabel, BorderLayout.NORTH);
      add(room.getImageLabel(), BorderLayout.CENTER); // Example image for the hotel
      add(priceLabel, BorderLayout.SOUTH);
    }
  }
}
