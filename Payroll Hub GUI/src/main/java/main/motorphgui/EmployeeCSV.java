/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.motorphgui;
import com.opencsv.bean.CsvBindByName;

/**
 *
 * @author Kristel
 */
public class EmployeeCSV {
    @CsvBindByName(column = "EmployeeId")
    private int employeeId;

    @CsvBindByName(column = "LastName")
    private String lastName;

    @CsvBindByName(column = "FirstName")
    private String firstName;

    @CsvBindByName(column = "Age")
    private int age;

    @CsvBindByName(column = "Position")
    private String position;

    @CsvBindByName(column = "BasicSalary")
    private float basicSalary;

    @CsvBindByName(column = "Allowance")
    private float allowance;

    @CsvBindByName(column = "SSS")
    private String sss;

    @CsvBindByName(column = "PhilHealth")
    private String philHealth;

    @CsvBindByName(column = "TIN")
    private String tin;

    @CsvBindByName(column = "PagIbig")
    private String pagIbig;

    // Getters and setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public float getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(float basicSalary) {
        this.basicSalary = basicSalary;
    }

    public float getAllowance() {
        return allowance;
    }

    public void setAllowance(float allowance) {
        this.allowance = allowance;
    }

    public String getSss() {
        return sss;
    }

    public void setSss(String sss) {
        this.sss = sss;
    }

    public String getPhilHealth() {
        return philHealth;
    }

    public void setPhilHealth(String philHealth) {
        this.philHealth = philHealth;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getPagIbig() {
        return pagIbig;
    }

    public void setPagIbig(String pagIbig) {
        this.pagIbig = pagIbig;
    }
}
