/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.motorphgui;

/**
 *
 * @author WINDOWS 10
 */
public class Admin {
    private int adminId;
    private String name;
    private String privileges;

    public Admin(int adminId, String name, String privileges) {
        this.adminId = adminId;
        this.name = name;
        this.privileges = privileges;
    }

    public void manageEmployee() {
        // Implementation to manage employee
        System.out.println("Managing Employee...");
    }

    public void managePayroll() {
        // Implementation to manage payroll
        System.out.println("Managing Payroll...");
    }

    public void manageDepartment() {
        // Implementation to manage department
        System.out.println("Managing Department...");
    }
    
}
