package main.java.com.example.Poo.controller;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.java.com.example.Poo.model.Date;

public class ReservationController {
  private static final String url = "jdbc:mysql://localhost:3306/mydatabase";
  private static final String user = "postgres";
  private static final String password = "postgres";

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

    try (Connection conn = DriverManager.getConnection(url, user, password);
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
    try (Connection conn = DriverManager.getConnection(url, user, password);
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
    try (Connection conn = DriverManager.getConnection(url, user, password);
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
}
