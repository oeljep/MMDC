/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.motorphgui;

/**
 *
 * @author WINDOWS 10
 */
import java.util.ArrayList;
import java.util.List;

public class Department {
    private int departmentId;
    private String departmentName;
    private int managerId;
    private List<Employee> employees = new ArrayList<>();

    public Department(int departmentId, String departmentName, int managerId) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.managerId = managerId;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    public Department getDepartmentDetails() {
        return this;
    }

    // Getters and Setters
}

