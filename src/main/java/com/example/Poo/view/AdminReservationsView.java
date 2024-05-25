package main.java.com.example.Poo.view;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import main.java.com.example.Poo.controller.*;
import main.java.com.example.Poo.model.Reservation;

class myTableModel extends DefaultTableModel {
  public myTableModel() {
    super();
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }
}

public class AdminReservationsView extends JFrame {
  private myTableModel tableModel;
  private JTextField reservationID;
  private JButton acceptButton;
  private JButton declineButton;
  private JLabel switchClickabale;

  public AdminReservationsView(int screenHeight, int screenWidth) {
    setTitle("Admin Reservations");
    setSize(screenWidth, screenHeight);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    initComponents();
    setupUI();
  }

  private void initComponents() {
    tableModel = new myTableModel();
    JTable resTable = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(resTable);

    reservationID = new JTextField(10);
    acceptButton = new JButton("Accept");
    declineButton = new JButton("Decline");
    switchClickabale = new JLabel("Room management →");
    switchClickabale.setForeground(Color.BLUE);

    switchClickabale.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            dispose();
            new RoomManagementView(getHeight(), getWidth(), new RoomManagementController())
                .setVisible(true);
          }
        });

    tableModel.addColumn("Rservations ID");
    tableModel.addColumn("Client");
    tableModel.addColumn("Room Number");
    tableModel.addColumn("Check In");
    tableModel.addColumn("Check Out");
    tableModel.addColumn("Price");

    resTable.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            int row = resTable.getSelectedRow();
            reservationID.setText(tableModel.getValueAt(row, 0).toString());
          }
        });

    acceptButton.addActionListener(
        e -> {
          if (reservationID.getText() == "") {
            JOptionPane.showMessageDialog(
                this, "Please enter a reservation ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
          }
          acceptRservation();
        });

    declineButton.addActionListener(
        e -> {
          if (reservationID.getText() == "") {
            JOptionPane.showMessageDialog(
                this, "Please enter a reservation ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
          }
          removeReservation();
        });

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(scrollPane, BorderLayout.CENTER);

    JPanel deletePanel = new JPanel(new FlowLayout());
    deletePanel.add(new JLabel("Reservation ID:"));
    deletePanel.add(reservationID);
    deletePanel.add(acceptButton);
    deletePanel.add(declineButton);
    deletePanel.add(switchClickabale);

    showAllReservations();

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

  private void removeReservation() {
    try {
      int reservationidInt = Integer.parseInt(reservationID.getText());
      ReservationController.removeReservation(reservationidInt);
      reservationID.setText("");
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(
          this, "Invalid reservation number!", "Error", JOptionPane.ERROR_MESSAGE);
    }
    showAllReservations();
  }

  private void acceptRservation() {
    try {
      int reservationidInt = Integer.parseInt(reservationID.getText());
      ReservationController.acceptReservation(reservationidInt);
      for (Reservation res : ReservationController.getAllReservations()) {
        if (res.getReservationID() == reservationidInt) {
          res.setAccepted(true);
        }
      }

      reservationID.setText("");
      showAllReservations();
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(
          this, "Invalid resrvation details !", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void showAllReservations() {
    List<Reservation> reservations = ReservationController.getAllReservations();
    tableModel.setRowCount(0);
    for (Reservation res : reservations) {
      Object[] rowData = {
        res.getReservationID(),
        res.getUserID(),
        res.getRoomNumber(),
        res.getCheckInDate(),
        res.getCheckOutDate(),
        res.getTotalPrice()
      };
      tableModel.addRow(rowData);
    }
  }
}
