package main.java.com.example.Poo.controller;

import main.java.com.example.Poo.model.Room;

import java.util.List;

public class RoomManagementController {
  private final Room room;

  public RoomManagementController(Room room) {
    this.room = room;
  }

  public void addRoom(int roomNumber, String type, boolean available, double pricePerNight) {
    room.addRoom(roomNumber, type, available, pricePerNight);
  }

  public void removeRoom(int roomNumber) {
    room.removeRoom(roomNumber);
  }

  public List<Room> getAllRooms() {
    return room.getAllRooms();
  }
}
