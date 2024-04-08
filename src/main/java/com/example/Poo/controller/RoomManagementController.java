package main.java.com.example.Poo.controller;

import java.awt.*;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JLabel;
import main.java.com.example.Poo.model.Room;

public class RoomManagementController {
  private JLabel imageLabel;
  private final Room room;

  public RoomManagementController() {
    this.room = new Room();
  }

  public void addRoom(
      int roomNumber, String type, boolean available, double pricePerNight, JLabel imageLabel) {
    Room newRoom = new Room(roomNumber, type, available, pricePerNight, imageLabel);
    newRoom.addRoom();
    // room.addRoom(roomNumber, type, available, pricePerNight);
  }

  public void removeRoom(int roomNumber) {
    room.removeRoom(roomNumber);
  }

  public void updateRoom(int roomNumber, String columnName, Object data) {
    room.updateRoom(roomNumber, columnName, data);
  }

  public JLabel uploadImage(JFrame frame) {
    this.imageLabel = new JLabel();
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(frame);

    if (result == JFileChooser.APPROVE_OPTION) {
      try {
        File file = fileChooser.getSelectedFile();
        Image image = ImageIO.read(file);

        // Defer setting the icon until after the label has been added to the frame
        SwingUtilities.invokeLater(
            () -> {
              // Resize the image to fit the label
              Image scaledImage = image.getScaledInstance(300, 400, Image.SCALE_SMOOTH);
              imageLabel.setIcon(new ImageIcon(scaledImage));
            });

        return this.imageLabel;
      } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(
            frame, "Error loading image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
    // Handle case when user cancels file chooser or error occurs
    return null; // Return null or a default label if appropriate
  }

  public List<Room> getAllRooms() {
    return room.getAllRooms();
  }
}
