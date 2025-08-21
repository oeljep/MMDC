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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EmployeeTableFrame extends JFrame {

    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private List<Employee> employeeList; 
    private final String csvFilePath = "data/employee.csv";

    private JTextField txtEmployeeId, txtFirstName, txtLastName, txtSSS, txtPhilHealth, txtTIN, txtPagibig;
    private JButton btnUpdate, btnDelete;

    public EmployeeTableFrame() {
        setTitle("MotorPH Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setMinimumSize(new Dimension(1000, 700));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        String[] columnNames = {"Employee ID", "Last Name", "First Name", "SSS", "PhilHealth", "TIN", "Pag-IBIG"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employeeTable = new JTable(tableModel);
        employeeTable.setRowHeight(25);
        employeeTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        employeeTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        employeeTable.setGridColor(Color.LIGHT_GRAY);
        employeeTable.setShowGrid(true);
        employeeTable.setSelectionBackground(new Color(220, 235, 252));
        employeeTable.setSelectionForeground(Color.BLACK);
        employeeTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        loadEmployees();

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        tablePanel.setBackground(Color.LIGHT_GRAY);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.setMinimumSize(new Dimension(100, 0));
        tablePanel.setPreferredSize(new Dimension(700, 600));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 8, 8));
        formPanel.setBackground(Color.LIGHT_GRAY);

        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2), //
                BorderFactory.createEmptyBorder(20, 20, 20, 20) // internal padding
        ));

        txtEmployeeId = new JTextField();
        txtEmployeeId.setEditable(false);
        txtLastName = new JTextField();
        txtFirstName = new JTextField();
        txtSSS = new JTextField();
        txtPhilHealth = new JTextField();
        txtTIN = new JTextField();
        txtPagibig = new JTextField();

        formPanel.add(new JLabel("Employee ID*:"));
        formPanel.add(txtEmployeeId);
        formPanel.add(new JLabel("Last Name*:"));
        formPanel.add(txtLastName);
        formPanel.add(new JLabel("First Name*:"));
        formPanel.add(txtFirstName);
        formPanel.add(new JLabel("SSS*:"));
        formPanel.add(txtSSS);
        formPanel.add(new JLabel("PhilHealth*:"));
        formPanel.add(txtPhilHealth);
        formPanel.add(new JLabel("TIN*:"));
        formPanel.add(txtTIN);
        formPanel.add(new JLabel("Pag-IBIG*:"));
        formPanel.add(txtPagibig);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tablePanel);
        splitPane.setResizeWeight(0.0);
        splitPane.setDividerLocation(350);
        splitPane.setOneTouchExpandable(true);
        splitPane.setPreferredSize(new Dimension(1200, 700));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        buttonPanel.setBackground(Color.WHITE);

        String[] buttonLabels = {"View Employee", "New Employee", "Update", "Delete", "Refresh"};
        JButton[] buttons = new JButton[buttonLabels.length];
        Color primary = new Color(45, 140, 240);

        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setFocusPainted(false);
            buttons[i].setBackground(primary);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFont(new Font("Segoe UI", Font.BOLD, 13));
            buttons[i].setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
            buttonPanel.add(buttons[i]);
        }

        JButton btnViewPayslip = buttons[0];
        JButton btnAdd = buttons[1];
        btnUpdate = buttons[2];
        btnDelete = buttons[3];
        JButton btnRefresh = buttons[4];
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

        btnViewPayslip.addActionListener(e -> viewPayslip());
        btnAdd.addActionListener(e -> new NewEmployeeForm(this).setVisible(true));
        btnUpdate.addActionListener(e -> updateEmployee());
        btnDelete.addActionListener(e -> deleteEmployee());
        btnRefresh.addActionListener(e -> {
            loadEmployees();
            adjustTableHeight();
        });

        JButton btnLogout = new JButton("Logout");
        btnLogout.setFocusPainted(false);
        btnLogout.setBackground(new Color(200, 50, 50));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogout.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                SwingUtilities.invokeLater(() -> new LoginGUI());
            }
        });

        employeeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && employeeTable.getSelectedRow() != -1) {
                populateFields(employeeTable.getSelectedRow());
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);
            }
        });

        buttonPanel.add(btnLogout);

        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void loadEmployees() {
        try {
            employeeList = CSVHandler.loadEmployees("data/employee.csv");
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading employee data:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Employee emp : employeeList) {
            tableModel.addRow(new Object[]{
                emp.getEmployeeId(),
                emp.getLastName(),
                emp.getFirstName(),
                emp.getGovernmentDetails().getSssNumber(),
                emp.getGovernmentDetails().getPhilHealthNumber(),
                emp.getGovernmentDetails().getTinNumber(),
                emp.getGovernmentDetails().getPagIbigNumber()
            });
        }
    }

    private void populateFields(int row) {
        txtEmployeeId.setText(tableModel.getValueAt(row, 0).toString());
        txtLastName.setText(tableModel.getValueAt(row, 1).toString());
        txtFirstName.setText(tableModel.getValueAt(row, 2).toString());
        txtSSS.setText(tableModel.getValueAt(row, 3).toString());
        txtPhilHealth.setText(tableModel.getValueAt(row, 4).toString());
        txtTIN.setText(tableModel.getValueAt(row, 5).toString());
        txtPagibig.setText(tableModel.getValueAt(row, 6).toString());
    }

    public void adjustTableHeight() {
        int rowCount = tableModel.getRowCount();
        int rowHeight = employeeTable.getRowHeight();
        int headerHeight = employeeTable.getTableHeader().getPreferredSize().height;
        int totalHeight = (rowCount * rowHeight) + headerHeight + 20;
        Dimension size = new Dimension(employeeTable.getPreferredSize().width, totalHeight);
        employeeTable.setPreferredSize(size);
    }

    private void viewPayslip() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            int empId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            Employee selectedEmployee = employeeList.stream()
                    .filter(e -> e.getEmployeeId() == empId)
                    .findFirst()
                    .orElse(null);
            if (selectedEmployee != null) {
                SwingUtilities.invokeLater(() -> {
                    ViewEmployeeFrame frame = new ViewEmployeeFrame(selectedEmployee);
                    frame.setVisible(true);
                    dispose();
                });
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee");
        }
    }

    private void updateEmployee() {
        try {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an employee to update!", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Get updated values from text fields
            String firstName = txtFirstName.getText().trim();
            String lastName = txtLastName.getText().trim();
            String sss = txtSSS.getText().trim();
            String phil = txtPhilHealth.getText().trim();
            String tin = txtTIN.getText().trim();
            String pagibig = txtPagibig.getText().trim();

            // Validation checks
            if (firstName.isEmpty() || lastName.isEmpty()
                    || sss.isEmpty() || phil.isEmpty() || tin.isEmpty() || pagibig.isEmpty()) {

                JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!sss.matches("\\d+") || !phil.matches("\\d+") || !tin.matches("\\d+") || !pagibig.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "SSS, PhilHealth, TIN, and Pag-IBIG numbers must contain digits only!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update Employee object
            Employee emp = employeeList.get(selectedRow);
            emp.setFirstName(firstName);
            emp.setLastName(lastName);

            GovernmentDetails gov = emp.getGovernmentDetails();
            gov.setSssNumber(sss);
            gov.setPhilHealthNumber(phil);
            gov.setTinNumber(tin);
            gov.setPagIbigNumber(pagibig);
            emp.setGovernmentDetails(gov);

            // Save updated list to CSV
            CSVHandler.saveEmployees(employeeList, "data/employee.csv");
            refreshTable();
            clearFields();
                    
            JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this, "Error updating employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this employee?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int empId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                employeeList.removeIf(e -> e.getEmployeeId() == empId);
                CSVHandler.saveEmployees(employeeList, "data/employee.csv");
                refreshTable();
                adjustTableHeight();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.");
        }
    }
    
    private void clearFields() {
        txtLastName.setText("");
        txtFirstName.setText("");
        txtSSS.setText("");
        txtPhilHealth.setText("");
        txtTIN.setText("");
        txtPagibig.setText("");
    }
}
