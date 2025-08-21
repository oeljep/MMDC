/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.motorphgui;

import java.time.LocalDate;


/**
 *
 * @author WINDOWS 10
 */
public class Leave {
     private int leaveId;
    private int employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    public Leave(int leaveId, int employeeId, LocalDate startDate, LocalDate endDate, String status) {
        this.leaveId = leaveId;
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Leave requestLeave() {
        this.status = "Requested";
        return this;
    }

    public Leave approveLeave() {
        this.status = "Approved";
        return this;
    }

    public Leave rejectLeave() {
        this.status = "Rejected";
        return this;
    }

    // Getters and Setters
    
}
