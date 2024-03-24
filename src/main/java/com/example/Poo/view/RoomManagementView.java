package main.java.com.example.Poo.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RoomManagementView extends JFrame {
  private JTable roomTable;
  private int screenWidth;
  private int screenHeight;
  private DefaultTableModel tableModel;
  private JTextField roomNumberField;
  private JTextField typeField;
  private JCheckBox availableCheckBox;
  private JTextField priceField;
  private JButton addButton;
  private JButton removeButton;
  private JTextField deletedRoomNumber;

  public RoomManagementView(int screenHeight, int screenWidth) {
    this.screenHeight = screenHeight;
    this.screenWidth = screenWidth;
    setTitle("Room Management");
    setSize(this.screenWidth, this.screenHeight);
    // set the font size for all components
    UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 15));
    UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 15));
    UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 15));
    UIManager.put("CheckBox.font", new Font("Arial", Font.PLAIN, 15));
    UIManager.put("Table.font", new Font("Arial", Font.PLAIN, 15));
    UIManager.put("TableHeader.font", new Font("Arial", Font.PLAIN, 15));

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // Create components
    tableModel = new DefaultTableModel();
    roomTable = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(roomTable);
    // set the size of the scroll pane
    roomNumberField = new JTextField(10);
    typeField = new JTextField(10);
    availableCheckBox = new JCheckBox("Available");
    priceField = new JTextField(10);
    addButton = new JButton("Add Room");
    removeButton = new JButton("Remove Room");
    deletedRoomNumber = new JTextField(10);

    // Add components to layout
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(scrollPane, BorderLayout.CENTER);

    JPanel additionPanel = new JPanel(new FlowLayout());
    JPanel deletePanel = new JPanel(new FlowLayout());
    deletePanel.add(new JLabel("Room Number:"));
    deletePanel.add(deletedRoomNumber);
    deletePanel.add(removeButton);

    additionPanel.add(new JLabel("Room Number:"));
    additionPanel.add(roomNumberField);
    additionPanel.add(new JLabel("Type:"));
    additionPanel.add(typeField);
    additionPanel.add(availableCheckBox);
    additionPanel.add(new JLabel("Price per Night:"));
    additionPanel.add(priceField);
    additionPanel.add(addButton);

    panel.add(additionPanel, BorderLayout.NORTH);
    panel.add(deletePanel, BorderLayout.SOUTH);
    add(panel);
    // Add columns to table
    tableModel.addColumn("Room Number");
    tableModel.addColumn("Type");
    tableModel.addColumn("Available");
    tableModel.addColumn("Price per Night");

    removeButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            removeRoom();
          }
        });

    // Action listener for Add Room button
    addButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            addRoom();
          }
        });
  }

  private void removeRoom() {
    int roomNumber = Integer.parseInt(deletedRoomNumber.getText());
    for (int i = 0; i < tableModel.getRowCount(); i++) {
      if (tableModel.getValueAt(i, 0).equals(roomNumber)) {
        tableModel.removeRow(i);
        break;
      }
    }
    deletedRoomNumber.setText("");
  }

  private void addRoom() {
    // Get data from input fields
    int roomNumber = Integer.parseInt(roomNumberField.getText());
    String type = typeField.getText();
    boolean available = availableCheckBox.isSelected();
    double pricePerNight = Double.parseDouble(priceField.getText());

    // Add data to table
    Object[] rowData = {roomNumber, type, available, pricePerNight};
    tableModel.addRow(rowData);

    // Clear input fields
    roomNumberField.setText("");
    typeField.setText("");
    availableCheckBox.setSelected(false);
    priceField.setText("");
  }
}
