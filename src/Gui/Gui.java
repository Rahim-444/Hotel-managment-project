package Gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class Gui extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField text;
    private JButton button;
    private String[] columnNames = { "Name", "Value" };
    private Object[][] data = { { "A", "1" }, { "B", "2" }, { "C", "3" } };

    public Gui() {
        super("Gui");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        add(pane, BorderLayout.CENTER);

        text = new JTextField();
        button = new JButton("Add");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.addRow(new Object[] { text.getText(), "0" });
            }
        });

        JPanel panel = new JPanel();
        panel.add(text);
        panel.add(button);
        add(panel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        Gui gui = new Gui();
        gui.setVisible(true);
    }
}
