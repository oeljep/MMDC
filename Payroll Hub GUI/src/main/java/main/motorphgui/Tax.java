/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.motorphgui;

/**
 *
 * @author Ayou
 */
public class Tax {

    private int taxId;
    private int employeeId;
    private float taxAmount;
    private float taxRate;

    public Tax(int taxId, int employeeId, float taxRate) {
        this.taxId = taxId;
        this.employeeId = employeeId;
        this.taxRate = taxRate;
    }

    public void calculateTax(float salary) {
        this.taxAmount = salary * taxRate;
    }

    public float getTaxAmount() {
        return taxAmount;
    }
}
