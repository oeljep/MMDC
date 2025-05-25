/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package payrollhubgui.dashboards;

/**
 *
 * @author rowel
 */
import javax.swing.*;
import java.awt.event.*;

public class PayslipDashboard extends JFrame {

    private JTextField txtEmployeeId;
    private JTextField txtPayCoverage;
    private JTextArea outputArea;

    public PayslipDashboard() {
        setTitle("Payroll Hub");
        setSize(320, 350);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JLabel lblEmployeeId = new JLabel("Employee ID:");
        lblEmployeeId.setBounds(20, 20, 100, 25);

        txtEmployeeId = new JTextField();
        txtEmployeeId.setBounds(130, 20, 150, 25);

        JLabel lblPayCoverage = new JLabel("Pay Coverage (days):");
        lblPayCoverage.setBounds(20, 60, 150, 25);

        txtPayCoverage = new JTextField();
        txtPayCoverage.setBounds(170, 60, 110, 25);

        JButton btnGenerate = new JButton("Generate Payslip");
        btnGenerate.setBounds(80, 100, 160, 30);

        outputArea = new JTextArea();
        outputArea.setBounds(20, 150, 260, 100);
        outputArea.setEditable(false);

        btnGenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generatePayslip();
            }
        });

        add(lblEmployeeId);
        add(txtEmployeeId);
        add(lblPayCoverage);
        add(txtPayCoverage);
        add(btnGenerate);
        add(outputArea);
    }

    private void generatePayslip() {
        try {
            int empId = Integer.parseInt(txtEmployeeId.getText().trim());
            int payDays = Integer.parseInt(txtPayCoverage.getText().trim());

            if (payDays <= 0) {
                throw new IllegalArgumentException("Pay coverage must be greater than 0.");
            }

            // Mock employee (replace with actual logic as needed)
            Employee emp = new Employee("Aldrin John Tamayo"); // assumes per-day salary
            float basicSalary = emp.getSalary() * payDays;

            Tax tax = new Tax(101, empId, 0.12f);
            tax.calculateTax(basicSalary);

            Payroll payroll = new Payroll(1001, empId, basicSalary, 2000f, tax.getTaxAmount());
            payroll.calculateSalary();

            outputArea.setText(
                    "Employee ID: " + empId
                    + "\nPosition: " + emp.getPosition()
                    + "\nPay Days: " + payDays
                    + "\nGross Salary: ₱" + basicSalary
                    + "\nTax: ₱" + tax.getTaxAmount()
                    + "\nNet Salary: ₱" + payroll.getNetSalary()
            );

        } catch (NumberFormatException ex) {
            outputArea.setText("Please enter valid numbers.");
        } catch (IllegalArgumentException ex) {
            outputArea.setText(ex.getMessage());
        } catch (Exception ex) {
            outputArea.setText("Unexpected error: " + ex.getMessage());
        }
        
   
    }
}
