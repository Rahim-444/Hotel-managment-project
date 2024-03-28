 package main.java.com.example.Poo.view;
 import main.java.com.example.Poo.model.Room;
 import main.java.com.example.Poo.controller.RoomManagementController;

// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import javax.swing.*;
// import javax.swing.table.DefaultTableModel;

// public class RoomManagementView extends JFrame {
//   private JTable roomTable;
//   private int screenWidth;
//   private int screenHeight;
//   private DefaultTableModel tableModel;
//   private JTextField roomNumberField;
//   private JTextField typeField;
//   private JCheckBox availableCheckBox;
//   private JTextField priceField;
//   private JButton addButton;
//   private JButton removeButton;
//   private JTextField deletedRoomNumber;

//   public RoomManagementView(int screenHeight, int screenWidth) {
//     this.screenHeight = screenHeight;
//     this.screenWidth = screenWidth;
//     setTitle("Room Management");
//     setSize(this.screenWidth, this.screenHeight);
//     // set the font size for all components
//     UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 15));
//     UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 15));
//     UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 15));
//     UIManager.put("CheckBox.font", new Font("Arial", Font.PLAIN, 15));
//     UIManager.put("Table.font", new Font("Arial", Font.PLAIN, 15));
//     UIManager.put("TableHeader.font", new Font("Arial", Font.PLAIN, 15));

//     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//     setLocationRelativeTo(null);

//     // Create components
//     tableModel = new DefaultTableModel();
//     roomTable = new JTable(tableModel);
//     JScrollPane scrollPane = new JScrollPane(roomTable);
//     // set the size of the scroll pane
//     roomNumberField = new JTextField(10);
//     typeField = new JTextField(10);
//     availableCheckBox = new JCheckBox("Available");
//     priceField = new JTextField(10);
//     addButton = new JButton("Add Room");
//     removeButton = new JButton("Remove Room");
//     deletedRoomNumber = new JTextField(10);

//     // Add components to layout
//     JPanel panel = new JPanel(new BorderLayout());
//     panel.add(scrollPane, BorderLayout.CENTER);

//     JPanel additionPanel = new JPanel(new FlowLayout());
//     JPanel deletePanel = new JPanel(new FlowLayout());
//     deletePanel.add(new JLabel("Room Number:"));
//     deletePanel.add(deletedRoomNumber);
//     deletePanel.add(removeButton);

//     additionPanel.add(new JLabel("Room Number:"));
//     additionPanel.add(roomNumberField);
//     additionPanel.add(new JLabel("Type:"));
//     additionPanel.add(typeField);
//     additionPanel.add(availableCheckBox);
//     additionPanel.add(new JLabel("Price per Night:"));
//     additionPanel.add(priceField);
//     additionPanel.add(addButton);

//     panel.add(additionPanel, BorderLayout.NORTH);
//     panel.add(deletePanel, BorderLayout.SOUTH);
//     add(panel);
//     // Add columns to table
//     tableModel.addColumn("Room Number");
//     tableModel.addColumn("Type");
//     tableModel.addColumn("Available");
//     tableModel.addColumn("Price per Night");

//     removeButton.addActionListener(
//         new ActionListener() {
//           @Override
//           public void actionPerformed(ActionEvent e) {
//             removeRoom();
//           }
//         });

//     // Action listener for Add Room button
//     addButton.addActionListener(
//         new ActionListener() {
//           @Override
//           public void actionPerformed(ActionEvent e) {
//             addRoom();
//           }
//         });
//   }

//   private void removeRoom() {
//     int roomNumber = Integer.parseInt(deletedRoomNumber.getText());
//     for (int i = 0; i < tableModel.getRowCount(); i++) {
//       if (tableModel.getValueAt(i, 0).equals(roomNumber)) {
//         tableModel.removeRow(i);
//         break;
//       }
//     }
//     deletedRoomNumber.setText("");
//   }

//   private void addRoom() {
//     // Get data from input fields
//     int roomNumber = Integer.parseInt(roomNumberField.getText());
//     String type = typeField.getText();
//     boolean available = availableCheckBox.isSelected();
//     double pricePerNight = Double.parseDouble(priceField.getText());

//     // Add data to table
//     Object[] rowData = {roomNumber, type, available, pricePerNight};
//     tableModel.addRow(rowData);

//     // Clear input fields
//     roomNumberField.setText("");
//     typeField.setText("");
//     availableCheckBox.setSelected(false);
//     priceField.setText("");
//   }
// }


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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

    public RoomManagementView(int screenHeight, int screenWidth, RoomManagementController controller) {
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

        JPanel showAllPanel = new JPanel(new FlowLayout());
        showAllPanel.add(showAllButton);

        panel.add(additionPanel, BorderLayout.NORTH);
        panel.add(deletePanel, BorderLayout.SOUTH);
        panel.add(showAllPanel, BorderLayout.SOUTH);
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
            JOptionPane.showMessageDialog(this, "Invalid room number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid room details!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAllRooms() {
        // Call the controller method to fetch all rooms from the database
        List<Room> rooms = controller.getAllRooms();
        // Clear existing table data
        tableModel.setRowCount(0);
        // Populate table with fetched rooms
        for (Room room : rooms) {
            Object[] rowData = {room.getRoomNumber(), room.getType(), room.isAvailable(), room.getPricePerNight()};
            tableModel.addRow(rowData);
        }
    }
}

