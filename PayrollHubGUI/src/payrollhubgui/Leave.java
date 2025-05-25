/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payrollhubgui;

/**
 *
 * @author Ayou
 */
import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Leave {

    private int leaveId;
    private int employeeId;
    private Date startDate;
    private Date endDate;
    private String status;

    public Leave(int leaveId, int employeeId, Date startDate, Date endDate) {
        this.leaveId = leaveId;
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = "Pending";
    }

    public void requestLeave() {
        System.out.println("Leave requested from " + startDate + " to " + endDate);
    }

    public void approveLeave() {
        status = "Approved";
    }

    public void rejectLeave() {
        status = "Rejected";
    }
    
    public static class LeaveGUI extends JFrame {

        private int leaveId;
        private int employeeId;
        private Date startDate;
        private Date endDate;
        private String status;

        // GUI components
        private JTextField txtLeaveId, txtEmployeeId, txtStartDate, txtEndDate;
        private JTextArea txtOutput;
        private JButton btnRequest, btnApprove, btnReject;

        public LeaveGUI() {
            setTitle("Leave Request System");
            setSize(400, 350);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLayout(null);

            JLabel lblLeaveId = new JLabel("Leave ID:");
            lblLeaveId.setBounds(20, 20, 100, 25);
            add(lblLeaveId);

            txtLeaveId = new JTextField();
            txtLeaveId.setBounds(130, 20, 200, 25);
            add(txtLeaveId);

            JLabel lblEmployeeId = new JLabel("Employee ID:");
            lblEmployeeId.setBounds(20, 60, 100, 25);
            add(lblEmployeeId);

            txtEmployeeId = new JTextField();
            txtEmployeeId.setBounds(130, 60, 200, 25);
            add(txtEmployeeId);

            JLabel lblStartDate = new JLabel("Start Date (yyyy-MM-dd):");
            lblStartDate.setBounds(20, 100, 200, 25);
            add(lblStartDate);

            txtStartDate = new JTextField();
            txtStartDate.setBounds(200, 100, 130, 25);
            add(txtStartDate);

            JLabel lblEndDate = new JLabel("End Date (yyyy-MM-dd):");
            lblEndDate.setBounds(20, 140, 200, 25);
            add(lblEndDate);

            txtEndDate = new JTextField();
            txtEndDate.setBounds(200, 140, 130, 25);
            add(txtEndDate);

            btnRequest = new JButton("Request Leave");
            btnRequest.setBounds(20, 180, 150, 25);
            add(btnRequest);

            btnApprove = new JButton("Approve");
            btnApprove.setBounds(180, 180, 90, 25);
            add(btnApprove);

            btnReject = new JButton("Reject");
            btnReject.setBounds(280, 180, 80, 25);
            add(btnReject);

            txtOutput = new JTextArea();
            txtOutput.setBounds(20, 220, 340, 70);
            txtOutput.setEditable(false);
            add(txtOutput);

            // Button Listeners
            btnRequest.addActionListener(e -> requestLeave());
            btnApprove.addActionListener(e -> approveLeave());
            btnReject.addActionListener(e -> rejectLeave());

            setVisible(true);
        }

        public void requestLeave() {
            try {
                leaveId = Integer.parseInt(txtLeaveId.getText());
                employeeId = Integer.parseInt(txtEmployeeId.getText());
                startDate = new SimpleDateFormat("yyyy-MM-dd").parse(txtStartDate.getText());
                endDate = new SimpleDateFormat("yyyy-MM-dd").parse(txtEndDate.getText());
                status = "Pending";

                txtOutput.setText("Leave requested from " + startDate + " to " + endDate + "\nStatus: " + status);
            } catch (Exception e) {
                txtOutput.setText("Error: " + e.getMessage());
            }
        }

        public void approveLeave() {
            if (status != null) {
                status = "Approved";
                txtOutput.append("\nLeave approved.");
            } else {
                txtOutput.setText("No leave to approve.");
            }
        }

        public void rejectLeave() {
            if (status != null) {
                status = "Rejected";
                txtOutput.append("\nLeave rejected.");
            } else {
                txtOutput.setText("No leave to reject.");
            }
        }
    }
    
    public static void main(String[] args) {
        LeaveGUI gui = new LeaveGUI();
    }
}
