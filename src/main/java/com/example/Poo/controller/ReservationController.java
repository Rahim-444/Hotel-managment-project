package main.java.com.example.Poo.controller;

import main.java.com.example.Poo.model.Room;
import main.java.com.example.Poo.model.Reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReservationController {
    private static final String url = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String user = "postgres";
    private static final String password = "postgres";

    public boolean makeReservation(int userID, int roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        if (!isRoomAvailable(roomNumber, checkInDate, checkOutDate)) {
            System.out.println("Room is not available for the specified dates.");
            return false;
        }

        BigDecimal totalPrice = calculateTotalPrice(roomNumber, checkInDate, checkOutDate);

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Reservations (id, room_number, CheckInDate, CheckOutDate, TotalPrice) VALUES (?, ?, ?, ?, ?)")) {
            pstmt.setInt(1, userID);
            pstmt.setInt(2, roomNumber);
            pstmt.setDate(3, Date.valueOf(checkInDate));
            pstmt.setDate(4, Date.valueOf(checkOutDate));
            pstmt.setBigDecimal(5, totalPrice);

            pstmt.executeUpdate();

            System.out.println("Reservation successfully made.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error making reservation: " + e.getMessage());
            return false;
        }
    }

    public boolean isRoomAvailable(int roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM Reservations WHERE room_number = ? AND ((CheckInDate <= ? AND CheckOutDate >= ?) OR (CheckInDate <= ? AND CheckOutDate >= ?))")) {
            pstmt.setInt(1, roomNumber);
            pstmt.setDate(2, Date.valueOf(checkInDate));
            pstmt.setDate(3, Date.valueOf(checkInDate));
            pstmt.setDate(4, Date.valueOf(checkOutDate));
            pstmt.setDate(5, Date.valueOf(checkOutDate));

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

    public BigDecimal calculateTotalPrice(int roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        BigDecimal pricePerNight = getPricePerNight(roomNumber);

        long numNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        BigDecimal totalPrice = pricePerNight.multiply(BigDecimal.valueOf(numNights));

        return totalPrice;
    }

    public BigDecimal getPricePerNight(int roomNumber) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement("SELECT price_per_night FROM Rooms WHERE room_number = ?")) {
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

