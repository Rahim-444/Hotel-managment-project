package main.java.com.example.Poo.view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import main.java.com.example.Poo.controller.RoomManagementController;
import main.java.com.example.Poo.model.Room;

public class RoomManagementView extends JFrame {
  private DefaultTableModel tableModel;
  private JTextField roomNumberField;
  private JTextField typeField;
  private JCheckBox availableCheckBox;
  private JTextField priceField;
  private JButton addButton;
  private JButton removeButton;
  private JTextField deletedRoomNumber;
  private JButton showAllButton;

  private RoomManagementController controller;

  public RoomManagementView(
      int screenHeight, int screenWidth, RoomManagementController controller) {
    this.controller = controller;
    setTitle("Room Management");
    setSize(screenWidth, screenHeight);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    initComponents();
    setupUI();
  }

  private void initComponents() {
    tableModel = new DefaultTableModel();
    JTable roomTable = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(roomTable);

    roomNumberField = new JTextField(10);
    typeField = new JTextField(10);
    availableCheckBox = new JCheckBox("Available");
    priceField = new JTextField(10);
    addButton = new JButton("Add Room");
    removeButton = new JButton("Remove Room");
    deletedRoomNumber = new JTextField(10);
    showAllButton = new JButton("Show All Rooms");

    tableModel.addColumn("Room Number");
    tableModel.addColumn("Type");
    tableModel.addColumn("Available");
    tableModel.addColumn("Price per Night");

    addButton.addActionListener(e -> addRoom());
    removeButton.addActionListener(e -> removeRoom());
    showAllButton.addActionListener(e -> showAllRooms());

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(scrollPane, BorderLayout.CENTER);

    JPanel additionPanel = new JPanel(new FlowLayout());
    additionPanel.add(new JLabel("Room Number:"));
    additionPanel.add(roomNumberField);
    additionPanel.add(new JLabel("Type:"));
    additionPanel.add(typeField);
    additionPanel.add(availableCheckBox);
    additionPanel.add(new JLabel("Price per Night:"));
    additionPanel.add(priceField);
    additionPanel.add(addButton);

    JPanel deletePanel = new JPanel(new FlowLayout());
    deletePanel.add(new JLabel("Room Number:"));
    deletePanel.add(deletedRoomNumber);
    deletePanel.add(removeButton);

    showAllRooms();

    panel.add(additionPanel, BorderLayout.NORTH);
    panel.add(deletePanel, BorderLayout.SOUTH);
    add(panel);
  }

  private void setupUI() {
    UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 15));
    UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 15));
    UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 15));
    UIManager.put("CheckBox.font", new Font("Arial", Font.PLAIN, 15));
    UIManager.put("Table.font", new Font("Arial", Font.PLAIN, 15));
    UIManager.put("TableHeader.font", new Font("Arial", Font.PLAIN, 15));
  }

  private void removeRoom() {
    try {
      int roomNumber = Integer.parseInt(deletedRoomNumber.getText());
      controller.removeRoom(roomNumber);
      deletedRoomNumber.setText("");
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(
          this, "Invalid room number!", "Error", JOptionPane.ERROR_MESSAGE);
    }
    showAllRooms();
  }

  private void addRoom() {
    try {
      int roomNumber = Integer.parseInt(roomNumberField.getText());
      String type = typeField.getText();
      boolean available = availableCheckBox.isSelected();
      double pricePerNight = Double.parseDouble(priceField.getText());

      controller.addRoom(roomNumber, type, available, pricePerNight);

      roomNumberField.setText("");
      typeField.setText("");
      availableCheckBox.setSelected(false);
      priceField.setText("");
      showAllRooms();
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(
          this, "Invalid room details!", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void showAllRooms() {
    // Call the controller method to fetch all rooms from the database
    List<Room> rooms = controller.getAllRooms();
    // Clear existing table data
    tableModel.setRowCount(0);
    // Populate table with fetched rooms
    for (Room room : rooms) {
      Object[] rowData = {
        room.getRoomNumber(), room.getType(), room.isAvailable(), room.getPricePerNight()
      };
      tableModel.addRow(rowData);
    }
  }
}
