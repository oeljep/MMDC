    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.motorphgui;

/**
 *
 * @author Macky
 */
public class Employee {

    private int employeeId;
    private String lastName;
    private String firstName;
    private String position;
    private String immediateSupervisor;
    private GovernmentDetails governmentDetails;
    private CompensationDetails compensationDetails;

    public Employee(int id, String last, String first, GovernmentDetails gov, CompensationDetails comp) {
        this.employeeId = id;
        this.lastName = last;
        this.firstName = first;
        this.governmentDetails = gov;
        this.compensationDetails = comp;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return firstName + lastName;
    }

    public String getPosition() {
        return position;
    }

    public String getImmediateSupervisor() {
        return immediateSupervisor;
    }

    public GovernmentDetails getGovernmentDetails() {
        return governmentDetails;
    }

    public CompensationDetails getCompensationDetails() {
        return compensationDetails;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setImmediateSupervisor(String supervisor) {
        this.immediateSupervisor = supervisor;
    }

    public void setGovernmentDetails(GovernmentDetails governmentDetails) {
        this.governmentDetails = governmentDetails;
    }

    public void setCompensationDetails(CompensationDetails compensationDetails) {
        this.compensationDetails = compensationDetails;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
