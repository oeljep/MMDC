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
import java.awt.event.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class ViewEmployeeFrame extends JFrame {

    private JComboBox<String> cmbMonth;
    private JComboBox<String> cmbYear;
    private JTextArea txtPayslip;   
    private Employee employee;

    public ViewEmployeeFrame(Employee emp) {
        this.employee = emp;

        setTitle("View Employee Details");
        setSize(900, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 10));
        getContentPane().setBackground(Color.WHITE);

        // Top Panel with Grid
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridwidth = 1;
        
        // Helper method to add label-value pairs
        int row = 0;

        String[][] fields = {
            {"Employee ID:", String.valueOf(emp.getEmployeeId())},
            {"Last Name:", emp.getLastName()},
            {"First Name:", emp.getFirstName()},
            {"Position:", emp.getPosition()},
            {"Immediate Supervisor:", emp.getImmediateSupervisor()},
            {"SSS Number:", emp.getGovernmentDetails().getSssNumber()},
            {"Philhealth Number:", emp.getGovernmentDetails().getPhilHealthNumber()},
            {"TIN:", emp.getGovernmentDetails().getTinNumber()},
            {"Pag-IBIG Number:", emp.getGovernmentDetails().getPagIbigNumber()}
        };

        // Lay out fields 3 columns per row
        for (int i = 0; i < fields.length; i++) {
            int col = i % 3;
            row = i / 3;

            gbc.gridx = col * 2;
            gbc.gridy = row;
            gbc.weightx = 0;
            JLabel label = new JLabel(fields[i][0]);
            label.setFont(new Font("Segoe UI", Font.BOLD, 13));
            topPanel.add(label, gbc);

            gbc.gridx = col * 2 + 1;
            gbc.weightx = 1;
            JLabel value = new JLabel(fields[i][1]);
            value.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            topPanel.add(value, gbc);
        }

        // Add dropdowns
        row += 1;
        gbc.gridy = row;
        gbc.gridx = 0;
        topPanel.add(new JLabel("Select Month:"), gbc);

        gbc.gridx = 1;
        cmbMonth = new JComboBox<>();
        populateMonths();
        cmbMonth.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        topPanel.add(cmbMonth, gbc);

        gbc.gridx = 2;
        topPanel.add(new JLabel("Select Year:"), gbc);

        gbc.gridx = 3;
        cmbYear = new JComboBox<>();
        populateYears();
        cmbYear.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        topPanel.add(cmbYear, gbc);

        // Add compute button aligned right
        gbc.gridx = 4;
        JButton btnCompute = new JButton("Compute");
        styleButton(btnCompute);
        topPanel.add(btnCompute, gbc);

        add(topPanel, BorderLayout.NORTH);

        // Payslip Text Area 
        txtPayslip = new JTextArea(30, 80); // set preferred size
        txtPayslip.setFont(new Font("Consolas", Font.PLAIN, 13));
        txtPayslip.setEditable(false);
        txtPayslip.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        txtPayslip.setLineWrap(false);
        txtPayslip.setWrapStyleWord(false);
        
        // Wrap JTextArea inside JScrollPane
        JScrollPane scrollPane = new JScrollPane(txtPayslip);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Create center panel with FlowLayout to center the scrollPane
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(scrollPane);

        // Add to main layout
        add(centerPanel, BorderLayout.CENTER);
        
        txtPayslip.setMargin(new Insets(10, 20, 10, 20));
        
        

        //  Bottom Button 
        JButton btnBack = new JButton("Back to Dashboard");
        styleButton(btnBack);
        btnBack.setPreferredSize(new Dimension(200, 40));

        JPanel southPanel = new JPanel();
        southPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        southPanel.setBackground(Color.WHITE);
        southPanel.add(btnBack);
        add(southPanel, BorderLayout.SOUTH);

        //  Action Logic 
        btnBack.addActionListener(e -> {
            new EmployeeTableFrame().setVisible(true);
            dispose();
        });
        
        btnCompute.addActionListener(e -> generatePayslip());
        populateMonths();
        setVisible(true);

    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(45, 140, 240));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }

    private void populateMonths() {
        cmbMonth.removeAllItems();
        String[] months = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };
         
        for (String m : months) {
            cmbMonth.addItem(m);
        }
        if (cmbMonth.getItemCount() > 0) {
            cmbMonth.setSelectedIndex(0);
        }
    }
    
    private void populateYears() {
        cmbYear.removeAllItems();

        List<String> availableYears = PayrollCalculator.getAvailableYearsForEmployee(employee.getEmployeeId());

        if (availableYears.isEmpty()) {
            cmbYear.addItem("No Records");
            cmbYear.setEnabled(false);
        } else {
            for (String year : availableYears) {
                cmbYear.addItem(year);
            }
            cmbYear.setEnabled(true);
            cmbYear.setSelectedIndex(0);
        }
    }

    private void generatePayslip() {
        String selectedMonth = (String) cmbMonth.getSelectedItem();
        if (selectedMonth != null) {
        int month = Month.valueOf(selectedMonth.toUpperCase()).getValue();
        int year = Integer.parseInt((String) cmbYear.getSelectedItem());
        
         List<AttendanceRecord> records = AttendanceLoader.loadAttendanceRecords("data/attendance.csv");
        AttendanceLoader loader = new AttendanceLoader();
        float totalHours = loader.calculateMonthlyHoursWorked(employee.getEmployeeId(), month, year, records);
        
            String payslip = PayrollCalculator.generatePayslip(employee.getEmployeeId(), selectedMonth, totalHours);
            txtPayslip.setText(payslip);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a valid month.");
        }
    }
}
        
