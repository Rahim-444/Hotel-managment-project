package main.java.com.example.Poo.controller;

import javax.swing.*;

public class HotelController {
  private String roomId;
  private double roomPrice;
  private boolean isAvailable;
  private ImageIcon roomImage;

  public HotelController(
      String roomId, double roomPrice, boolean isAvailable, ImageIcon roomImage) {
    this.roomId = roomId;
    this.roomPrice = roomPrice;
    this.isAvailable = isAvailable;
    this.roomImage = roomImage;
  }

  public String getRoomId() {
    return roomId;
  }

  public void setRoomId(String roomId) {
    this.roomId = roomId;
  }

  public double getRoomPrice() {
    return roomPrice;
  }

  public void setRoomPrice(double roomPrice) {
    this.roomPrice = roomPrice;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean isAvailable) {
    this.isAvailable = isAvailable;
  }

  public ImageIcon getRoomImage() {
    return roomImage;
  }

  public void setRoomImage(ImageIcon roomImage) {
    this.roomImage = roomImage;
  }
}
