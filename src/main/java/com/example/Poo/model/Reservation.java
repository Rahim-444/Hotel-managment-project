package main.java.com.example.Poo.model;

import java.time.LocalDate;

public class Reservation {
  private int reservationID;
  private int userID;
  private int roomNumber;
  private LocalDate checkInDate;
  private LocalDate checkOutDate;
  private double totalPrice;

  // Constructors
  public Reservation(
      int reservationID,
      int userID,
      int roomNumber,
      LocalDate checkInDate,
      LocalDate checkOutDate,
      double totalPrice) {
    this.reservationID = reservationID;
    this.userID = userID;
    this.roomNumber = roomNumber;
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
    this.totalPrice = totalPrice;
  }

  // Getters and Setters
  public int getReservationID() {
    return reservationID;
  }

  public void setReservationID(int reservationID) {
    this.reservationID = reservationID;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public int getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(int roomNumber) {
    this.roomNumber = roomNumber;
  }

  public LocalDate getCheckInDate() {
    return checkInDate;
  }

  public void setCheckInDate(LocalDate checkInDate) {
    this.checkInDate = checkInDate;
  }

  public LocalDate getCheckOutDate() {
    return checkOutDate;
  }

  public void setCheckOutDate(LocalDate checkOutDate) {
    this.checkOutDate = checkOutDate;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  // Optional: Override toString() method for easier debugging
  @Override
  public String toString() {
    return "Reservation{"
        + "reservationID="
        + reservationID
        + ", userID="
        + userID
        + ", roomNumber="
        + roomNumber
        + ", checkInDate="
        + checkInDate
        + ", checkOutDate="
        + checkOutDate
        + ", totalPrice="
        + totalPrice
        + '}';
  }
}
