package main.java.com.example.Poo.view;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomManagementView extends JFrame {
    private JTable roomTable;
    private DefaultTableModel tableModel;
    private JTextField roomNumberField;
    private JTextField typeField;
    private JCheckBox availableCheckBox;
    private JTextField priceField;
    private JButton addButton;

    public RoomManagementView() {
        setTitle("Room Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        tableModel = new DefaultTableModel();
        roomTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(roomTable);
        roomNumberField = new JTextField(10);
        typeField = new JTextField(10);
        availableCheckBox = new JCheckBox("Available");
        priceField = new JTextField(10);
        addButton = new JButton("Add Room");

        // Add components to layout
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new FlowLayout());
        formPanel.add(new JLabel("Room Number:"));
        formPanel.add(roomNumberField);
        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeField);
        formPanel.add(availableCheckBox);
        formPanel.add(new JLabel("Price per Night:"));
        formPanel.add(priceField);
        formPanel.add(addButton);

        panel.add(formPanel, BorderLayout.SOUTH);
        add(panel);

        // Action listener for Add Room button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRoom();
            }
        });
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RoomManagementView view = new RoomManagementView();
                view.setVisible(true);
            }
        });
    }
}

