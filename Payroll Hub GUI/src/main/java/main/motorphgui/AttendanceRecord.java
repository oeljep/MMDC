/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.motorphgui;
import com.opencsv.bean.CsvBindByName;
import java.time.LocalDate;
import java.time.LocalTime;



/**
 *
 * @author Kristel
 */
public class AttendanceRecord {
    
     @CsvBindByName(column = "Employee #")
    private int employeeId;

    @CsvBindByName(column = "Date")
    private String dateStr; // raw date from CSV

    @CsvBindByName(column = "Log In")
    private String timeInStr; // raw time from CSV

    @CsvBindByName(column = "Log Out")
    private String timeOutStr; // raw time from CSV
    
    @CsvBindByName (column = "Rate")
    private float rate;
    
    @CsvBindByName (column = "Hours Worked")
    private float hoursWorked;
    
    @CsvBindByName (column = "OT Hrs")
    private float overtimeHours;
    
    
    private LocalDate date;
    private LocalTime timeIn;
    private LocalTime timeOut;
    
    public void finalizeParsing() {
        this.date = LocalDate.parse(dateStr.trim());
        this.timeIn = LocalTime.parse(timeInStr.trim());
        this.timeOut = LocalTime.parse(timeOutStr.trim());
    }

    public float getDurationHoursWorked() {
        if (timeOut == null || timeIn == null) return 0;
        return (float) java.time.Duration.between(timeIn, timeOut).toMinutes() / 60;
    }
    // Getters
    public int getEmployeeId() {
        return employeeId;
    }

    public LocalDate getDate() {
        return date;
    }
     public LocalTime getTimeIn() {
        return timeIn;
    }

    public LocalTime getTimeOut() {
        return timeOut;
    }
    
    public float getRate(){
        return rate;
    
    }
    
    public float getHoursWorked(){
        return hoursWorked;
        
    }
    
    public float getOvertimeHours(){
        return overtimeHours;
        
    
    }
    
    public float getTotalHoursWorked(){
        return hoursWorked + overtimeHours;
    }

}
