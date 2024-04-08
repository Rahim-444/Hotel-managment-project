package main.java.com.example.Poo.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

// import graphics

public class Room {
  private int roomNumber;
  private String type;
  private boolean available;
  private double pricePerNight;
  private JLabel imageLabel;

  // Database connection parameters
  private String url = "jdbc:postgresql://localhost:5432/mydatabase";
  private String user = "postgres";
  private String password = "postgres";

  // Constructor
  public Room(
      int roomNumber, String type, boolean available, double pricePerNight, JLabel imageLabel) {
    this.roomNumber = roomNumber;
    this.type = type;
    this.available = available;
    this.pricePerNight = pricePerNight;
    this.imageLabel = imageLabel;
  }

  public Room() {}

  // Method to establish database connection
  private Connection connect() throws SQLException {
    return DriverManager.getConnection(url, user, password);
  }

  public int getRoomNumber() {
    return roomNumber;
  }

  public JLabel getImageLabel() {
    return imageLabel;
  }

  public void setRoomNumber(int roomNumber) {
    this.roomNumber = roomNumber;
  }

  public void updateRoom(int roomNumber, String columnName, Object data) {
    String sql = "UPDATE rooms SET " + columnName + " = ? WHERE room_number = ?";
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setObject(1, data);
      pstmt.setInt(2, roomNumber);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public double getPricePerNight() {
    return pricePerNight;
  }

  public void setPricePerNight(double pricePerNight) {
    this.pricePerNight = pricePerNight;
  }

  // check if the table rooms exists
  public void checkTable() {
    String sql =
        "CREATE TABLE IF NOT EXISTS rooms (room_number INT PRIMARY KEY, type TEXT,available"
            + " BOOLEAN, price_per_night DOUBLE PRECISION, image BYTEA)";
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void addRoom() {
    checkTable();
    String sql =
        "INSERT INTO rooms (room_number, type, available, price_per_night, image) VALUES (?, ?, ?,"
            + " ?, ?)";
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, roomNumber);
      pstmt.setString(2, type);
      pstmt.setBoolean(3, available);
      pstmt.setDouble(4, pricePerNight);

      try {
        byte[] imageData = getImageData(imageLabel.getIcon());
        pstmt.setBytes(5, imageData);
      } catch (IOException e) {
        e.printStackTrace();
      }

      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  // icon to byte array
  private byte[] getImageData(Icon icon) throws IOException {
    BufferedImage image =
        new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics g = image.getGraphics();
    icon.paintIcon(null, g, 0, 0);
    g.dispose();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(image, "png", baos);
    baos.flush();
    byte[] imageData = baos.toByteArray();
    baos.close();
    return imageData;
  }

  public void removeRoom(int roomNumber) {
    String sql = "DELETE FROM rooms WHERE room_number = ?";
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, roomNumber);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public List<Room> getAllRooms() {
    String sql = "SELECT * FROM rooms";
    List<Room> rooms = new ArrayList<>();
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {
      while (rs.next()) {
        Room room =
            new Room(
                rs.getInt("room_number"),
                rs.getString("type"),
                rs.getBoolean("available"),
                rs.getDouble("price_per_night"),
                // FIX:might cause error
                new JLabel(new ImageIcon((byte[]) rs.getObject("image"))));
        rooms.add(room);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return rooms;
  }
}
