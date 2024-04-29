package main.java.com.example.Poo.view;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import main.java.com.example.Poo.model.Date;

public class DatePicker extends JFrame {
  private JPanel panel;
  private JButton previousMonthButton;
  private JButton nextMonthButton;
  private JLabel monthYearLabel;
  private JPanel calendarPanel;
  public Date selectedDate;
  public Date selectedEndDate;
  private boolean firstSelected = false;

  private Calendar currentCalendar;

  public DatePicker() {
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    panel = new JPanel();
    panel.setLayout(new BorderLayout());

    JPanel headerPanel = new JPanel(new FlowLayout());
    previousMonthButton = new JButton("<<");
    nextMonthButton = new JButton(">>");
    monthYearLabel = new JLabel();
    headerPanel.add(previousMonthButton);
    headerPanel.add(monthYearLabel);
    headerPanel.add(nextMonthButton);
    panel.add(headerPanel, BorderLayout.NORTH);

    calendarPanel = new JPanel(new GridLayout(0, 7));
    panel.add(calendarPanel, BorderLayout.CENTER);

    getContentPane().add(panel);
    pack();
    setVisible(true);

    currentCalendar = Calendar.getInstance();
    updateCalendar();
    previousMonthButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            currentCalendar.add(Calendar.MONTH, -1);
            updateCalendar();
          }
        });

    nextMonthButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            currentCalendar.add(Calendar.MONTH, 1);
            updateCalendar();
          }
        });
  }

  private void updateCalendar() {
    monthYearLabel.setText(getMonthYearString(currentCalendar));

    calendarPanel.removeAll();

    Calendar calendar = (Calendar) currentCalendar.clone();
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    int firstDayOfWeek = calendar.getFirstDayOfWeek();

    for (int i = 0; i < 7; i++) {
      JLabel label = new JLabel(getShortDayOfWeekString(i + firstDayOfWeek));
      label.setHorizontalAlignment(SwingConstants.CENTER);
      calendarPanel.add(label);
    }

    calendar.set(Calendar.DAY_OF_MONTH, 1);
    int maxDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    int emptySlots = (currentDayOfWeek - firstDayOfWeek + 7) % 7;

    for (int i = 0; i < emptySlots; i++) {
      calendarPanel.add(new JLabel(""));
    }

    for (int i = 1; i <= maxDaysInMonth; i++) {
      final int day = i;
      JButton dayButton = new JButton(Integer.toString(day));
      dayButton.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              if (!firstSelected) {
                selectedDate =
                    new Date(
                        currentCalendar.get(Calendar.YEAR),
                        currentCalendar.get(Calendar.MONTH) + 1,
                        day);
                dayButton.setBorder(BorderFactory.createLineBorder(Color.RED));
                firstSelected = true;
              } else {
                selectedEndDate =
                    new Date(
                        currentCalendar.get(Calendar.YEAR),
                        currentCalendar.get(Calendar.MONTH) + 1,
                        day);
                if (selectedEndDate.CompareTo(selectedDate) < 0) {
                  JOptionPane.showMessageDialog(null, "End date must be after start date");
                  selectedEndDate = null;
                  selectedDate = null;
                }

                dispose();
              }
            }
          });
      calendarPanel.add(dayButton);
    }

    pack();
  }

  private String getMonthYearString(Calendar calendar) {
    return new SimpleDateFormat("MMMM yyyy").format(calendar.getTime());
  }

  private String getShortDayOfWeekString(int dayOfWeek) {
    String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    return daysOfWeek[dayOfWeek % 7];
  }
}
