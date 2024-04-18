package main.java.com.example.Poo.view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import main.java.com.example.Poo.controller.RoomManagementController;
import main.java.com.example.Poo.model.Room;

public class HotelsView extends JFrame {
  private JPanel panel;
  private JLabel label;
  private JTextField searchField;
  private RoomManagementController controller;
  private JButton clear;
  private List<Room> rooms;

  public HotelsView(int height, int width, RoomManagementController controller) {
    this.controller = controller;
    this.rooms = controller.getAllRooms();
    setTitle("Rooms");
    setSize(width, height);
    setLocationRelativeTo(null);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    panel = new JPanel(new GridLayout(0, 3, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    label = new JLabel("Search for a room:");
    label.setFont(new Font("Arial", Font.BOLD, 24));
    searchField = new JTextField();
    searchField.setPreferredSize(new Dimension(1000, 30));
    searchField.addActionListener(e -> handleSearch());
    clear = new JButton("Clear");
    clear.addActionListener(e -> drawRooms());
    JPanel searchPanel = new JPanel(new BorderLayout());
    searchPanel.add(searchField, BorderLayout.WEST);
    searchPanel.add(clear, BorderLayout.CENTER);
    JPanel container = new JPanel(new BorderLayout());
    container.add(label, BorderLayout.NORTH);
    container.add(searchPanel, BorderLayout.SOUTH);

    add(container, BorderLayout.NORTH);
    JScrollPane pane = new JScrollPane(panel);
    pane.setBackground(Color.pink);
    add(pane, BorderLayout.CENTER);

    for (Room room : rooms) {
      panel.add(new HotelCard(room));
    }

    setVisible(true);
  }

  private void handleSearch() {
    panel.removeAll();
    panel.revalidate();
    panel.repaint();
    for (Room room : rooms) {
      if (room.getType().contains(searchField.getText())) {
        panel.add(new HotelCard(room));
      }
    }
  }

  private void drawRooms() {
    searchField.setText("");
    panel.removeAll();
    panel.revalidate();
    panel.repaint();
    for (Room room : rooms) {
      panel.add(new HotelCard(room));
    }
  }

  private class HotelCard extends JPanel {
    public HotelCard(Room room) {
      setPreferredSize(new Dimension(200, 150));
      setBackground(Color.white);
      setBorder(BorderFactory.createLineBorder(Color.gray, 1));
      setLayout(new BorderLayout());

      JLabel nameLabel = new JLabel(room.getType());
      nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

      JLabel priceLabel = new JLabel("$" + room.getPricePerNight());
      priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

      add(nameLabel, BorderLayout.NORTH);
      add(room.getImageLabel(), BorderLayout.CENTER);
      add(priceLabel, BorderLayout.SOUTH);
      // when the card is clicked it will be taken to more detailed section about
      // the hotel
      addMouseListener(
          new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
              RoomsView roomsView = new RoomsView(room);
              roomsView.setVisible(true);
            }
          });
    }
  }
}
