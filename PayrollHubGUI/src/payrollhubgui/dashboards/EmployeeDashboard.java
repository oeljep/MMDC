/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payrollhubgui.dashboards;

import javax.swing.*;
import java.awt.event.*;
import payrollhubgui.Employee;
import payrollhubgui.Leave.LeaveGUI;

public class EmployeeDashboard extends Employee {

    private JFrame frame;
    private JTextField txtName, txtAge, txtPosition;
    private JTextArea outputArea;

    public EmployeeDashboard(int employeeId, String name, int age, String position, float salary) {
        super(employeeId, name, age, position, salary);
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Employee Dashboard");
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display current employee details at top as labels (non-editable)
        JLabel lblCurrentName = new JLabel("Name: " + getName());
        lblCurrentName.setBounds(10, 10, 350, 25);
        frame.add(lblCurrentName);

        JLabel lblCurrentAge = new JLabel("Age: " + getAge());
        lblCurrentAge.setBounds(10, 40, 350, 25);
        frame.add(lblCurrentAge);

        JLabel lblCurrentPosition = new JLabel("Position: " + getPosition());
        lblCurrentPosition.setBounds(10, 70, 350, 25);
        frame.add(lblCurrentPosition);

        // Editable fields for updating profile
        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(10, 110, 100, 25);
        frame.add(lblName);

        txtName = new JTextField(getName());
        txtName.setBounds(120, 110, 250, 25);
        frame.add(txtName);

        JLabel lblAge = new JLabel("Age:");
        lblAge.setBounds(10, 150, 100, 25);
        frame.add(lblAge);

        txtAge = new JTextField(String.valueOf(getAge()));
        txtAge.setBounds(120, 150, 250, 25);
        frame.add(txtAge);

        JLabel lblPosition = new JLabel("Position:");
        lblPosition.setBounds(10, 190, 100, 25);
        frame.add(lblPosition);

        txtPosition = new JTextField(getPosition());
        txtPosition.setBounds(120, 190, 250, 25);
        frame.add(txtPosition);

        // Buttons
        JButton btnUpdate = new JButton("Update Profile");
        btnUpdate.setBounds(10, 230, 150, 30);
        frame.add(btnUpdate);

        JButton btnViewPayslip = new JButton("View Payslip");
        btnViewPayslip.setBounds(180, 230, 150, 30);
        frame.add(btnViewPayslip);

        JButton btnLeave = new JButton("Apply Leave");
        btnLeave.setBounds(100, 270, 150, 30);
        frame.add(btnLeave);

        // Output area for feedback and messages
        outputArea = new JTextArea();
        outputArea.setBounds(10, 310, 360, 80);
        outputArea.setEditable(false);
        frame.add(outputArea);

        // Button action listeners
        btnUpdate.addActionListener(e -> updateProfileFromGUI(lblCurrentName, lblCurrentAge, lblCurrentPosition));
        btnViewPayslip.addActionListener(e -> viewPayslipFromGUI());
        btnLeave.addActionListener(e -> applyLeaveFromGUI());

        frame.setVisible(true);
    }
    private void updateProfileFromGUI(JLabel lblName, JLabel lblAge, JLabel lblPosition) {
        try {
            String newName = txtName.getText().trim();
            int newAge = Integer.parseInt(txtAge.getText().trim());
            String newPosition = txtPosition.getText().trim();

            if (newName.isEmpty() || newPosition.isEmpty() || newAge <= 0) {
                outputArea.setText("Please enter valid name, age, and position.");
                return;
            }

            // Call superclass method to update profile data
            updateProfile(newName, newAge, newPosition);

            // Update top labels to reflect changes
            lblName.setText("Name: " + getName());
            lblAge.setText("Age: " + getAge());
            lblPosition.setText("Position: " + getPosition());

            outputArea.setText("Profile updated successfully.");
        } catch (NumberFormatException ex) {
            outputArea.setText("Please enter a valid number for age.");
        }
    }
    
    private void updateProfileFromGUI() {
        try {
            String newName = txtName.getText().trim();
            int newAge = Integer.parseInt(txtAge.getText().trim());
            String newPosition = txtPosition.getText().trim();

            if (newName.isEmpty() || newPosition.isEmpty() || newAge <= 0) {
                throw new IllegalArgumentException("All fields must be valid.");
            }

            updateProfile(newName, newAge, newPosition);
            outputArea.setText("Profile updated successfully.\nName: " + newName + "\nAge: " + newAge + "\nPosition: " + newPosition);
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid input for age.");
        } catch (IllegalArgumentException ex) {
            outputArea.setText(ex.getMessage());
        }
    }

    private void viewPayslipFromGUI() {
        new PayslipDashboard().setVisible(true);
    }

    private void applyLeaveFromGUI() {
        new LeaveGUI().setVisible(true);
    } 
    
}
