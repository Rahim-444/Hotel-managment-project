package main.java.com.example.Poo.view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import main.java.com.example.Poo.controller.*;
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
  private JButton refreshButton;
  private JButton uploadImageButton;
  private JLabel imageLabel;
  private JTextField roomDesc;

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
    refreshButton = new JButton("Refresh");
    uploadImageButton = new JButton("Upload Image");
    roomDesc = new JTextField(10);
    refreshButton.addActionListener(e -> showAllRooms());

    tableModel.addColumn("Room Number");
    tableModel.addColumn("Type");
    tableModel.addColumn("Available");
    tableModel.addColumn("Price per Night");
    tableModel.addColumn("Description");
    tableModel.addTableModelListener(
        new TableModelListener() {
          @Override
          public void tableChanged(TableModelEvent e) {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (column != -1) {
              int roomNumber = (int) tableModel.getValueAt(row, 0);
              String columnName = tableModel.getColumnName(column);
              Object data;
              if (columnName.equals("Available")) {
                String input = (String) tableModel.getValueAt(row, column);
                input = input.toLowerCase().trim();
                if ("true".equals(input)) {
                  data = true;
                } else {
                  data = false;
                }
              } else if (columnName.equals("Price per Night")) {
                columnName = "price_per_night";
                data = Double.parseDouble((String) tableModel.getValueAt(row, column));
              } else if (columnName.equals("Description")) {
                columnName = "description";
                data = tableModel.getValueAt(row, column);
              } else if (columnName.equals("Room Number")) {
                System.out.println("Room number cannot be changed!");
                return;
              } else {
                data = tableModel.getValueAt(row, column);
              }
              controller.updateRoom(roomNumber, columnName, data);
            }
          }
        });

    addButton.addActionListener(
        e -> {
          if (imageLabel == null) {
            JOptionPane.showMessageDialog(
                this, "Please upload an image!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
          }
          addRoom();
        });
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
    additionPanel.add(new JLabel("Description:"));
    additionPanel.add(roomDesc);
    additionPanel.add(uploadImageButton);
    additionPanel.add(addButton);
    uploadImageButton.addActionListener(
        e -> {
          this.imageLabel = controller.uploadImage(this);
        });

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
      String desc = roomDesc.getText();

      controller.addRoom(roomNumber, type, available, pricePerNight, imageLabel, desc);

      roomNumberField.setText("");
      typeField.setText("");
      availableCheckBox.setSelected(false);
      priceField.setText("");
      roomDesc.setText("");
      this.imageLabel = null;
      showAllRooms();
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(
          this, "Invalid room details!", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void showAllRooms() {
    List<Room> rooms = controller.getAllRooms();
    tableModel.setRowCount(0);
    for (Room room : rooms) {
      Object[] rowData = {
        room.getRoomNumber(),
        room.getType(),
        room.isAvailable(),
        room.getPricePerNight(),
        room.getDescription()
      };
      tableModel.addRow(rowData);
    }
  }
}
