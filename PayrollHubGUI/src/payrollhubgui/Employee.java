/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payrollhubgui;

/**
 *
 * @author Ayou
 */
public class Employee {

    private int employeeId;
    private String name;
    public int age;
    private String position;
    private float salary;

    public Employee(int employeeId, String name, int age, String position, float salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.age = age;
        this.position = position;
        this.salary = salary;
    }

    public void viewPayslip() {
        System.out.println("Payslip for " + name + ": Salary = PHP" + salary);
    }

    public void updateProfile(String name, int age, String position) {
        this.name = name;
        this.age = age;
        this.position = position;
    }

    public void applyLeave() {
        System.out.println(name + " applied for leave.");
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public float getSalary() {
        return salary;
    }
    

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }
    
    public int getAge() {
        return age;
    }
}
