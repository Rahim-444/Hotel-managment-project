package main.java.com.example.Poo.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private int roomNumber;
    private String type;
    private boolean available;
    private double pricePerNight;

    // Database connection parameters
    private String url = "jdbc:postgresql://localhost:5432/mydatabase";
    private String user = "postgres";
    private String password = "postgres";

    // Constructor
    public Room(int roomNumber, String type, boolean available, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.available = available;
        this.pricePerNight = pricePerNight;
    }

    // Method to establish database connection
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

      public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
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

    public void addRoom(int roomNumber, String type, boolean available, double pricePerNight) {
        String sql = "INSERT INTO rooms (room_number, type, available, price_per_night) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roomNumber);
            pstmt.setString(2, type);
            pstmt.setBoolean(3, available);
            pstmt.setDouble(4, pricePerNight);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
                Room room = new Room(
                        rs.getInt("room_number"),
                        rs.getString("type"),
                        rs.getBoolean("available"),
                        rs.getDouble("price_per_night"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rooms;
    }
}

