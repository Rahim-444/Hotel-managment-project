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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

// import graphics

public class Room {
  private static int id = 0;
  private int roomNumber;
  private String type;
  private boolean available;
  private double pricePerNight;
  private JLabel imageLabel;
  private String desc;
  private LocalDate startDate = LocalDate.of(2024, 5, 10);
  private LocalDate endDate = LocalDate.now();
  private ArrayList<Reservation> reservations = new ArrayList<>();

  // Constructor
  public Room(
      int roomNumber,
      String type,
      boolean available,
      double pricePerNight,
      JLabel imageLabel,
      String desc) {
    this.roomNumber = roomNumber;
    this.type = type;
    this.available = available;
    this.pricePerNight = pricePerNight;
    this.imageLabel = imageLabel;
    this.desc = desc;
    id++;
    reservations.add(new Reservation(id, 1, 1, startDate, endDate, 100.0));
  }

  public Room() {}

  public Connection connect() throws SQLException {
    return DriverManager.getConnection(
        Database.getUrl(), Database.getUser(), Database.getPassword());
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

  public String getDescription() {
    return desc;
  }

  public void setDescription(String desc) {
    this.desc = desc;
  }

  // check if the table rooms exists
  public void checkTable() {
    String sql =
        "CREATE TABLE IF NOT EXISTS rooms (room_number INT PRIMARY KEY, type TEXT,available"
            + " BOOLEAN, price_per_night DOUBLE PRECISION, image BYTEA, description TEXT)";
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
        "INSERT INTO rooms (room_number, type, available, price_per_night, image, description)"
            + " VALUES (?, ?, ?, ?, ?, ?)";
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
      pstmt.setString(6, desc);

      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

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
                new JLabel(new ImageIcon((byte[]) rs.getObject("image"))),
                rs.getString("description"));
        rooms.add(room);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return rooms;
  }

  public ArrayList<Reservation> getReservations() {
    return reservations;
  }
}
