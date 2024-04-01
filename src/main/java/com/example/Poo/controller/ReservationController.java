package main.java.com.example.Poo.controller;

import main.java.com.example.Poo.model.Room;
import main.java.com.example.Poo.model.Reservation;

import java.beans.Statement;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationController {
  private Connection connection;

  public ReservationController() {
    try {
            // Establish connection to your PostgreSQL database
            String url = "jdbc:postgresql://localhost:5432/mydatabase";
            String username = "postgres";
            String password = "postgres";
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
  }

  public Reservation makeReservation(int roomId, int userId, LocalDateTime startTime, LocalDateTime endTime) {
    try {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO reservations (room_id, user_id, start_time, end_time) VALUES (?, ?, ?, ?)",
          Statement.RETURN_GENERATED_KEYS);
      statement.setInt(1, roomId);
      statement.setInt(2, userId);
      statement.setTimestamp(3, Timestamp.valueOf(startTime));
      statement.setTimestamp(4, Timestamp.valueOf(endTime));

      int affectedRows = statement.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Creating reservation failed, no rows affected.");
      }
      ResultSet generatedKeys = statement.getGeneratedKeys();
      if (generatedKeys.next()) {
        int id = generatedKeys.getInt(1);
        return new Reservation(id, roomId, userId, startTime, endTime);
      } else {
        throw new SQLException("Creating reservation failed, ,no ID obtained");
      }

    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }

}

  public List<Reservation> getAllReservations(){
    List<Reservation> reservations = new ArrayList<>();
    try{
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM reservations");
    while (resultSet.next()){
       int id = resultSet.getInt("id");
                int roomId = resultSet.getInt("room_id");
                int userId = resultSet.getInt("user_id");
                LocalDateTime startTime = resultSet.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = resultSet.getTimestamp("end_time").toLocalDateTime();
                reservations.add(new Reservation(id, roomId, userId, startTime, endTime));
    }
    } catch (SQLException e) {
            e.printStackTrace();
        }
    return reservations;
  }
}
