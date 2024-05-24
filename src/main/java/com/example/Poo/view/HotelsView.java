package main.java.com.example.Poo.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.*;
import main.java.com.example.Poo.controller.RoomManagementController;
import main.java.com.example.Poo.model.Room;
import main.java.com.example.Poo.model.User;

public class HotelsView extends JFrame {
  private JPanel panel;
  private JLabel label;
  private JTextField searchField;
  private RoomManagementController controller;
  private JButton clear;
  private List<Room> rooms;
  private User user;

  public HotelsView(int height, int width, RoomManagementController controller, User user) {
    this.controller = controller;
    this.rooms = this.controller.getAllRooms();
    this.user = user;
    setTitle("Rooms");
    setSize(width, height);
    setLocationRelativeTo(null);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    panel = new JPanel(new GridLayout(0, 4, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));
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
    pack();
  }

  private void handleSearch() {
    String text = searchField.getText();
    if (text.isEmpty()) {
      drawRooms();
    } else {
      panel.removeAll();
      panel.revalidate();
      panel.repaint();
      for (Room room : rooms) {
        if (room.getType().toLowerCase().contains(text.toLowerCase())) {
          panel.add(new HotelCard(room));
        }
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
    private Room room;
    private JLabel nameLabel, priceLabel, ratingLabel;
    private ImageIcon roomImage;

    public HotelCard(Room room) {
      this.room = room;
      setMaximumSize(new Dimension(250, 200));
      setPreferredSize(new Dimension(250, 200));
      setBackground(Color.WHITE);
      setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
      setLayout(null);

      nameLabel = new JLabel(this.room.getType());
      nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
      nameLabel.setBounds(10, 10, 230, 20);

      priceLabel = new JLabel("$" + room.getPricePerNight());
      priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
      priceLabel.setBounds(10, 170, 100, 20);

      ratingLabel = new JLabel("*****");
      ratingLabel.setFont(new Font("Arial", Font.BOLD, 12));
      ratingLabel.setBounds(200, 170, 40, 20);

      roomImage = (ImageIcon) room.getImageLabel().getIcon();

      JLabel imageLabel = new JLabel(roomImage);
      imageLabel.setBounds(10, 40, 230, 120);

      MouseListener hoverListener =
          new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
              setBackground(new Color(230, 230, 230));
            }

            @Override
            public void mouseExited(MouseEvent e) {
              setBackground(Color.WHITE);
            }
          };

      addMouseListener(hoverListener);
      addMouseListener(
          new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              new RoomsView(room, user);
            }
          });

      add(nameLabel);
      add(priceLabel);
      add(ratingLabel);
      add(imageLabel);
    }
  }
}
