package main.java.com.example.Poo.controller;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.java.com.example.Poo.model.Database;
import main.java.com.example.Poo.model.Date;
import main.java.com.example.Poo.model.Reservation;

public class ReservationController {

  public static Connection connect() throws SQLException {
    return DriverManager.getConnection(
        Database.getUrl(), Database.getUser(), Database.getPassword());
  }

  public boolean makeReservation(int userID, int roomNumber, Date checkInDate, Date checkOutDate) {
    java.sql.Date sqlCheckInDate =
        new java.sql.Date(checkInDate.year, checkInDate.month, checkInDate.day);
    java.sql.Date sqlCheckOutDate =
        new java.sql.Date(checkOutDate.year, checkOutDate.month, checkOutDate.day);
    System.out.println(sqlCheckInDate);
    System.out.println(checkInDate.year);
    if (!isRoomAvailable(roomNumber, sqlCheckInDate, sqlCheckOutDate)) {
      System.out.println("Room is not available for the specified dates.");
      return false;
    }

    BigDecimal totalPrice = calculateTotalPrice(roomNumber, checkInDate, checkOutDate);

    try (Connection conn =
            DriverManager.getConnection(
                Database.getUrl(), Database.getUser(), Database.getPassword());
        PreparedStatement pstmt =
            conn.prepareStatement(
                "INSERT INTO Reservations (id, room_number, CheckInDate, CheckOutDate, TotalPrice)"
                    + " VALUES (?, ?, ?, ?, ?)")) {
      pstmt.setInt(1, userID);
      pstmt.setInt(2, roomNumber);
      pstmt.setDate(3, sqlCheckInDate);
      pstmt.setDate(4, sqlCheckOutDate);
      pstmt.setBigDecimal(5, totalPrice);
      pstmt.executeUpdate();

      System.out.println("Reservation successfully made.");
      return true;
    } catch (SQLException e) {
      System.out.println("Error making reservation: " + e.getMessage());
      return false;
    }
  }

  public boolean isRoomAvailable(
      int roomNumber, java.sql.Date checkInDate, java.sql.Date checkOutDate) {
    try (Connection conn =
            DriverManager.getConnection(
                Database.getUrl(), Database.getUser(), Database.getPassword());
        PreparedStatement pstmt =
            conn.prepareStatement(
                "SELECT COUNT(*) FROM Reservations WHERE room_number = ? AND ((CheckInDate <= ? AND"
                    + " CheckOutDate >= ?) OR (CheckInDate <= ? AND CheckOutDate >= ?))")) {
      // FIX: just changed the order of the things i don't know if it
      // breaks the code
      pstmt.setInt(1, roomNumber);
      pstmt.setDate(2, checkInDate);
      pstmt.setDate(3, checkOutDate);
      pstmt.setDate(4, checkInDate);
      pstmt.setDate(5, checkOutDate);

      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          int count = rs.getInt(1);
          return count == 0;
        }
      }
    } catch (SQLException e) {
      System.out.println("Error checking room availability: " + e.getMessage());
    }
    return false;
  }

  public BigDecimal calculateTotalPrice(int roomNumber, Date checkInDate, Date checkOutDate) {
    BigDecimal pricePerNight = getPricePerNight(roomNumber);

    long numNights = Date.difference(checkOutDate, checkInDate);

    BigDecimal totalPrice = pricePerNight.multiply(BigDecimal.valueOf(numNights));

    return totalPrice;
  }

  public BigDecimal getPricePerNight(int roomNumber) {
    try (Connection conn =
            DriverManager.getConnection(
                Database.getUrl(), Database.getUser(), Database.getPassword());
        PreparedStatement pstmt =
            conn.prepareStatement("SELECT price_per_night FROM Rooms WHERE room_number = ?")) {
      pstmt.setInt(1, roomNumber);

      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          return rs.getBigDecimal("price_per_night");
        }
      }
    } catch (SQLException e) {
      System.out.println("Error retrieving price per night: " + e.getMessage());
    }
    return BigDecimal.ZERO;
  }

  public static List<Reservation> getAllReservations() {
    String sql = "SELECT * FROM Reservations";
    List<Reservation> reservations = new ArrayList<>();
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {
      while (rs.next()) {
        // TODO: Implement Reservation
        // Reservation reservation =
        // new Reservation(
        // rs.getInt("id"),
        // rs.getInt("room_number"),
        // rs.getDate("CheckInDate"),
        // rs.getDate("CheckOutDate"),
        // rs.getBigDecimal("TotalPrice"));
        // reservations.add(reservation);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return reservations;
  }

  public static boolean removeReservation(int reservationID) {
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Reservations WHERE id = ?")) {
      pstmt.setInt(1, reservationID);
      pstmt.executeUpdate();
      return true;
    } catch (SQLException e) {
      System.out.println("Error removing reservation: " + e.getMessage());
      return false;
    }
  }
}
