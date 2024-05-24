package main.java.com.example.Poo.view;

import java.awt.*;
import javax.swing.*;
import main.java.com.example.Poo.controller.ReservationController;
import main.java.com.example.Poo.model.Date;
import main.java.com.example.Poo.model.Room;

public class RoomsView extends JFrame {

  private static final long serialVersionUID = 1L;
  private JPanel panel;
  private JLabel roomName;
  private JLabel roomDescription;
  private JButton joinButton;
  private JButton cancelButton;
  private ReservationController reservationController = new ReservationController();
  public Date selectedDate;
  public Date selectedEndDate;
  public Date availableFrom = new Date(2024, 5, 4);
  public Date availableTo = new Date(2024, 12, 31);
  public JLabel roomAvailability;

  public RoomsView(Room room) {
    roomName = new JLabel(room.getType());
    roomName.setFont(new Font("Arial", Font.BOLD, 16));
    roomDescription =
        new JLabel("<html><p style='width: 550px'>" + room.getDescription() + "</p></html>");
    roomDescription.setFont(new Font("Arial", Font.PLAIN, 14));

    roomAvailability =
        new JLabel(
            "<html><div style='display: flex; color: red;'>"
                + "<div>Available from: "
                + availableFrom.year
                + "-"
                + availableFrom.month
                + "-"
                + availableFrom.day
                + "</div>"
                + "<div>Available to: "
                + availableTo.year
                + "-"
                + availableTo.month
                + "-"
                + availableTo.day
                + "</div>"
                + "</div></html>)");

    joinButton = new JButton("make reservation");
    cancelButton = new JButton("cancel reservation");

    panel = new JPanel();

    JPanel datePanel = new JPanel();
    datePanel.setLayout(new FlowLayout());
    JButton datePickerButton = new JButton("Choose Date Range");
    datePickerButton.addActionListener(
        e -> {
          DatePicker datePicker = new DatePicker(room);
          datePicker.addWindowListener(
              new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                  selectedDate = datePicker.selectedDate;
                  selectedEndDate = datePicker.selectedEndDate;
                  ((JButton) getContentPane().getComponent(2)).setEnabled(true);
                  ((JButton) getContentPane().getComponent(2))
                      .setText(
                          "Selected Date: "
                              + selectedDate.year
                              + "-"
                              + selectedDate.month
                              + "-"
                              + selectedDate.day
                              + " to "
                              + selectedEndDate.year
                              + "-"
                              + selectedEndDate.month
                              + "-"
                              + selectedEndDate.day);
                }
              });
        });
    datePanel.add(datePickerButton);

    getContentPane().setLayout(new BorderLayout());
    ImageIcon imageIcon = (ImageIcon) room.getImageLabel().getIcon();
    Image image = imageIcon.getImage();
    Image resizedImage = image.getScaledInstance(800, 450, Image.SCALE_SMOOTH);
    ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
    JLabel imageLabel = new JLabel();
    imageLabel.setIcon(resizedImageIcon);
    getContentPane().add(imageLabel, BorderLayout.NORTH);
    getContentPane()
        .add(
            new JPanel(new FlowLayout()) {
              {
                add(roomName);
                add(roomDescription);
                add(roomAvailability);
              }
            },
            BorderLayout.CENTER);
    getContentPane().add(datePanel, BorderLayout.SOUTH);

    joinButton.addActionListener(
        e -> {
          if (selectedDate == null || selectedEndDate == null) {
            JOptionPane.showMessageDialog(null, "Please select a date range");
            return;
          }
          reservationController.makeReservation(
              1, room.getRoomNumber(), selectedDate, selectedEndDate);
          if (selectedDate == null || selectedEndDate == null) {
            JOptionPane.showMessageDialog(null, "Please select a date range");
            return;
          }
          reservationController.makeReservation(
              1, room.getRoomNumber(), selectedDate, selectedEndDate);
        });
    datePanel.add(joinButton);

    cancelButton.addActionListener(
        e -> {
          // Implement functionality for leaving the room (e.g., confirmation dialog)
        });

    setSize(800, 630);
    setLocationRelativeTo(null);
    setVisible(true);
  }
}
