/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.motorphgui;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kristel
 */
public class AttendanceLoader {
    
    public static List<AttendanceRecord> loadAttendanceRecords(String filePath) {
        List<AttendanceRecord> records = new ArrayList<>();
        
        try (Reader reader = new FileReader(filePath)) {
            
           CsvToBean<AttendanceRecord> csvToBean = new CsvToBeanBuilder<AttendanceRecord>(reader)
                    .withType(AttendanceRecord.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            for (AttendanceRecord record : csvToBean) {
              try {
                record.finalizeParsing();
                records.add(record);
        }catch (Exception ex) {
            System.err.println("Skipping malformed row for employee ID: " + record.getEmployeeId());
            }
          }
        } catch (Exception e) {
            System.err.println("Error loading attendance file: " + e.getMessage());
             e.printStackTrace();
        }
        return records;
    }
    public float calculateMonthlyHoursWorked(int empId, int month, int year, List<AttendanceRecord> records) {
    float totalHours = 0;
    for (AttendanceRecord record : records) {
        if (record.getEmployeeId() == empId &&
            record.getDate().getMonthValue() == month &&
            record.getDate().getYear() == year) {
            totalHours += record.getTotalHoursWorked();
        }
    }
    return totalHours;
}
}
    
