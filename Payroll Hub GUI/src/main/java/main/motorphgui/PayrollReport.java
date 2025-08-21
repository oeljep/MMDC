/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.motorphgui;

/**
 *
 * @author WINDOWS 10
 */
public class PayrollReport {
  private int employeeId;
    private String period; // e.g., "May 2025"
    private float grossPay;
    private float tax;
    private float netPay;

    public PayrollReport(int employeeId, String period, float grossPay, float tax, float netPay) {
        this.employeeId = employeeId;
        this.period = period;
        this.grossPay = grossPay;
        this.tax = tax;
        this.netPay = netPay;
    }   
}
