/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.motorphgui;

import java.util.List;

/**
 *
 * @author Ayou
 */
public class Payroll {

    private int payrollId;
    private int employeeId;
    private float basicSalary;
    private float allowances;
    private float deductions;
    private float netSalary;

    public Payroll(int payrollId, int employeeId, float basicSalary, float allowances, float deductions) {
        this.payrollId = payrollId;
        this.employeeId = employeeId;
        this.basicSalary = basicSalary;
        this.allowances = allowances;
        this.deductions = deductions;
       
    }

    public void calculateSalary() {
        netSalary = basicSalary + allowances - deductions;
    }

    public float getNetSalary() {
        return netSalary;
    }
     public Payslip generatePayslip() {
        return new Payslip(employeeId, basicSalary, deductions, netSalary);
    }

    public List<Float> viewSalaryDetails() {
        return List.of(basicSalary, deductions, netSalary);
    }
    public PayrollReport generatePayrollReport(String period) {
        float gross = basicSalary;
        float tax = calculateTax();
        float net = calculateNetSalary();
        return new PayrollReport(employeeId, period, gross, tax, net);
    }

    // Getters and Setters

    private float calculateTax() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private float calculateNetSalary() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
