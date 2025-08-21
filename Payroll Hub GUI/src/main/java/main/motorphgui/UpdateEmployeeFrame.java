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
import java.util.List;

public class UpdateEmployeeFrame extends JFrame {

    private JTextField lastNameField, firstNameField, sssField, philHealthField, tinField, pagibigField,
            positionField, supervisorField, salaryField, riceField, phoneField, clothingField, hourlyField;

    private final Employee employee;
    private final List<Employee> employeeList;
    private final String csvPath;
    private final EmployeeTableFrame parent;

    public UpdateEmployeeFrame(Employee emp, List<Employee> employees, String csvPath, EmployeeTableFrame parent) {
        this.employee = emp;
        this.employeeList = employees;
        this.csvPath = csvPath;
        this.parent = parent;

        setTitle("Update Employee");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 680);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Styling
        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);
        Color bgColor = new Color(245, 245, 250);
        Color buttonColor = new Color(66, 133, 244);
        Color buttonTextColor = Color.WHITE;

        JPanel formPanel = new JPanel(new GridLayout(14, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        formPanel.setBackground(bgColor);

        // Add Fields
        lastNameField = addField(formPanel, "Last Name:", emp.getLastName(), labelFont, fieldFont);
        firstNameField = addField(formPanel, "First Name:", emp.getFirstName(), labelFont, fieldFont);
        sssField = addField(formPanel, "SSS #:", emp.getGovernmentDetails().getSssNumber(), labelFont, fieldFont);
        philHealthField = addField(formPanel, "PhilHealth #:", emp.getGovernmentDetails().getPhilHealthNumber(), labelFont, fieldFont);
        tinField = addField(formPanel, "TIN #:", emp.getGovernmentDetails().getTinNumber(), labelFont, fieldFont);
        pagibigField = addField(formPanel, "Pag-IBIG #:", emp.getGovernmentDetails().getPagIbigNumber(), labelFont, fieldFont);
        positionField = addField(formPanel, "Position:", emp.getPosition(), labelFont, fieldFont);
        supervisorField = addField(formPanel, "Supervisor:", emp.getImmediateSupervisor(), labelFont, fieldFont);
        salaryField = addField(formPanel, "Monthly Salary:", String.valueOf(emp.getCompensationDetails().getMonthlySalary()), labelFont, fieldFont);
        riceField = addField(formPanel, "Rice Subsidy:", String.valueOf(emp.getCompensationDetails().getRiceSubsidy()), labelFont, fieldFont);
        phoneField = addField(formPanel, "Phone Allowance:", String.valueOf(emp.getCompensationDetails().getPhoneAllowance()), labelFont, fieldFont);
        clothingField = addField(formPanel, "Clothing Allowance:", String.valueOf(emp.getCompensationDetails().getClothingAllowance()), labelFont, fieldFont);
        hourlyField = addField(formPanel, "Hourly Rate:", String.valueOf(emp.getCompensationDetails().getHourlyRate()), labelFont, fieldFont);

        // Save Button
        JButton saveButton = new JButton("Save Changes");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        saveButton.setBackground(buttonColor);
        saveButton.setForeground(buttonTextColor);
        saveButton.setFocusPainted(false);
        saveButton.setPreferredSize(new Dimension(140, 40));
        saveButton.addActionListener(e -> saveChanges());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(bgColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        buttonPanel.add(saveButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().setBackground(bgColor);
    }

    private JTextField addField(JPanel panel, String labelText, String initialValue, Font labelFont, Font fieldFont) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        panel.add(label);

        JTextField field = new JTextField(initialValue);
        field.setFont(fieldFont);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panel.add(field);
        return field;
    }

    private void saveChanges() {
        try {
            // Update basic info
            employee.setLastName(lastNameField.getText().trim());
            employee.setFirstName(firstNameField.getText().trim());
            employee.setPosition(positionField.getText().trim());
            employee.setImmediateSupervisor(supervisorField.getText().trim());

            // Update government details
            GovernmentDetails gov = employee.getGovernmentDetails();
            gov.setSssNumber(sssField.getText().trim());
            gov.setPhilHealthNumber(philHealthField.getText().trim());
            gov.setTinNumber(tinField.getText().trim());
            gov.setPagIbigNumber(pagibigField.getText().trim());

            // Update compensation
            CompensationDetails comp = employee.getCompensationDetails();
            comp.setMonthlySalary(Double.parseDouble(salaryField.getText().trim()));
            comp.setRiceSubsidy(Double.parseDouble(riceField.getText().trim()));
            comp.setPhoneAllowance(Double.parseDouble(phoneField.getText().trim()));
            comp.setClothingAllowance(Double.parseDouble(clothingField.getText().trim()));
            comp.setHourlyRate(Double.parseDouble(hourlyField.getText().trim()));

            // Save all updates to CSV
            CSVHandler.saveEmployees(employeeList, csvPath);

            parent.refreshTable();
            parent.adjustTableHeight();

            JOptionPane.showMessageDialog(this, "Updated Succesfully!");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to update employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
