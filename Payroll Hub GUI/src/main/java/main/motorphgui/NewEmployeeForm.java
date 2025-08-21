/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.motorphgui;

/**
 *
 * @author Macky
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NewEmployeeForm extends JFrame {

    private String[] labels = {
        "Employee ID* ",   // 0
        "Last Name* ",     // 1
        "First Name* ",    // 2
        "SSS* ",           // 3
        "PhilHealth* ",    // 4
        "TIN* ",           // 5
        "Pag-IBIG* "       // 6
    };

    private JTextField[] fields = new JTextField[labels.length];
    private EmployeeTableFrame parent;

    public NewEmployeeForm(EmployeeTableFrame parent) {
        this.parent = parent;

        setTitle("New Employee");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);
        Color bgColor = new Color(245, 245, 250);
        Color buttonColor = new Color(66, 133, 244);
        Color buttonTextColor = Color.WHITE;

        JPanel inputPanel = new JPanel(new GridLayout(labels.length, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        inputPanel.setBackground(bgColor);

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i] + ":");
            lbl.setFont(labelFont);
            inputPanel.add(lbl);

            fields[i] = new JTextField();
            fields[i].setFont(fieldFont);
            fields[i].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180)),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            inputPanel.add(fields[i]);
        }

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        saveButton.setBackground(buttonColor);
        saveButton.setForeground(buttonTextColor);
        saveButton.setFocusPainted(false);
        saveButton.setPreferredSize(new Dimension(120, 35));
        saveButton.addActionListener(e -> saveEmployee());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(bgColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        buttonPanel.add(saveButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().setBackground(bgColor);
        setSize(450, 400);
        setLocationRelativeTo(null);
    }

    private void saveEmployee() {
        String[] numericFields = {
            "Employee ID", "SSS", "PhilHealth", "TIN", "Pag-IBIG"
        };

        try {
            List<String> row = new ArrayList<>();

            for (int i = 0; i < fields.length; i++) {
                String input = fields[i].getText().trim();
                String label = labels[i].replace("* ", "").replace(":", "");

                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(this, label + " is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    fields[i].requestFocus();
                    return;
                }

                if (arrayContains(numericFields, label)) {
                    if (!input.matches("\\d+")) {
                        JOptionPane.showMessageDialog(this, label + " must be a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        fields[i].requestFocus();
                        return;
                    }
                }

                row.add(input);
            }

            // Add empty placeholders for removed columns to maintain CSV structure
            while (row.size() < 14) {
                row.add("");  // Fill remaining columns with blanks
            }

            // Save to CSV
            CSVHandler.appendEmployee(row, "data/employee.csv");

            // Reload employee table
            parent.loadEmployees();

            JOptionPane.showMessageDialog(this, "Employee data successfully saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean arrayContains(String[] array, String value) {
        for (String item : array) {
            if (item.equalsIgnoreCase(value)) return true;
        }
        return false;
    }
}
