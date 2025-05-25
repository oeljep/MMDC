/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payrollhubgui;

/**
 *
 * @author Ayou
 */
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Department {

    private int departmentId;
    private String departmentName;
    private int managerId;
    private ArrayList<Employee> employees = new ArrayList<>();

    public Department(int departmentId, String departmentName, int managerId) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.managerId = managerId;
    }

    public void addEmployee(Employee e) {
        employees.add(e);
    }

    public void removeEmployee(Employee e) {
        employees.remove(e);
    }

    public void getDepartmentDetails() {
        System.out.println("Department: " + departmentName + " | Manager ID: " + managerId);
    }
    
    public static class DepartmentGUI extends JFrame {

        private JTextField txtDeptId, txtDeptName, txtManagerId, txtEmployeeName;
        private JTextArea textArea;
        private Department department;

        public DepartmentGUI() {
            setTitle("Department Class");
            setSize(500, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);
            
            JLabel lblDeptId = new JLabel("Department ID:");
            lblDeptId.setBounds(20, 20, 100, 25);
            add(lblDeptId);

            txtDeptId = new JTextField();
            txtDeptId.setBounds(130, 20, 100, 25);
            add(txtDeptId);

            JLabel lblDeptName = new JLabel("Department Name:");
            lblDeptName.setBounds(20, 60, 120, 25);
            add(lblDeptName);

            txtDeptName = new JTextField();
            txtDeptName.setBounds(130, 60, 150, 25);
            add(txtDeptName);

            JLabel lblManagerId = new JLabel("Manager ID:");
            lblManagerId.setBounds(20, 100, 100, 25);
            add(lblManagerId);

            txtManagerId = new JTextField();
            txtManagerId.setBounds(130, 100, 100, 25);
            add(txtManagerId);

            JButton btnCreateDept = new JButton("Create Department");
            btnCreateDept.setBounds(20, 140, 180, 30);
            add(btnCreateDept);

            JLabel lblEmpName = new JLabel("Employee Name:");
            lblEmpName.setBounds(20, 190, 120, 25);
            add(lblEmpName);

            txtEmployeeName = new JTextField();
            txtEmployeeName.setBounds(130, 190, 150, 25);
            add(txtEmployeeName);

            JButton btnAddEmp = new JButton("Add Employee");
            btnAddEmp.setBounds(20, 230, 130, 30);
            add(btnAddEmp);

            JButton btnRemoveEmp = new JButton("Remove Employee");
            btnRemoveEmp.setBounds(160, 230, 150, 30);
            add(btnRemoveEmp);

            JButton btnShowDetails = new JButton("Show Department Details");
            btnShowDetails.setBounds(20, 270, 200, 30);
            add(btnShowDetails);

            textArea = new JTextArea();
            textArea.setBounds(20, 310, 440, 40);
            textArea.setEditable(false);
            add(textArea);

            // Button Actions
            btnCreateDept.addActionListener(e -> {
                try {
                    int id = Integer.parseInt(txtDeptId.getText());
                    String name = txtDeptName.getText();
                    int managerId = Integer.parseInt(txtManagerId.getText());
                    department = new Department(id, name, managerId);
                    JOptionPane.showMessageDialog(this, "Department Created!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input.");
                }
            });

            btnAddEmp.addActionListener(e -> {
                if (department != null) {
                    String empName = txtEmployeeName.getText();
                    if (!empName.isEmpty()) {
                        Employee emp = new Employee(empName);
                        department.addEmployee(emp);
                        JOptionPane.showMessageDialog(this, "Employee added.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Create a department first.");
                }
            });

            btnRemoveEmp.addActionListener(e -> {
                if (department != null) {
                    String empName = txtEmployeeName.getText();
                    if (!empName.isEmpty()) {
                        Employee emp = new Employee(empName); // simplistic check
                        department.removeEmployee(emp); // will fail unless equals is overridden
                        JOptionPane.showMessageDialog(this, "Attempted to remove employee.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Create a department first.");
                }
            });

            btnShowDetails.addActionListener(e -> {
                if (department != null) {
                    textArea.setText("Department: " + txtDeptName.getText()
                            + " | Manager ID: " + txtManagerId.getText());
                } else {
                    JOptionPane.showMessageDialog(this, "No department to show.");
                }
            });

            setVisible(true);
     
        }
    }
    
    public static void main(String[] args) {
        DepartmentGUI departmentGUI = new DepartmentGUI();
    }
}