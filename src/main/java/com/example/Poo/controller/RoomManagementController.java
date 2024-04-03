package main.java.com.example.Poo.controller;

import java.util.List;
import main.java.com.example.Poo.model.Room;

public class RoomManagementController {
  private final Room room;

  public RoomManagementController() {
    this.room = new Room();
  }

  public void addRoom(int roomNumber, String type, boolean available, double pricePerNight) {
    Room newRoom = new Room(roomNumber, type, available, pricePerNight);
    newRoom.addRoom();
    // room.addRoom(roomNumber, type, available, pricePerNight);
  }

  public void removeRoom(int roomNumber) {
    room.removeRoom(roomNumber);
  }

  public void updateRoom(int roomNumber, String columnName, Object data) {
    room.updateRoom(roomNumber, columnName, data);
  }

  public List<Room> getAllRooms() {
    return room.getAllRooms();
  }
}
