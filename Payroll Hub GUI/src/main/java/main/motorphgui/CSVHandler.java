/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.motorphgui;


/**
 *
 * @author Macky
 */
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class CSVHandler {

    public static List<String[]> readCSV(String filePath) {
        List<String[]> records = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                // Skip lines with all fields empty or less than 14 columns
                if (line.length < 14 || Arrays.stream(line).allMatch(String::isBlank)) {
                    continue;
                }
                records.add(line);
            }
        } catch (IOException | CsvValidationException e) {
            JOptionPane.showMessageDialog(null, "Error reading CSV: " + e.getMessage());
            e.printStackTrace();
        }
        return records;
    }

    public static List<Employee> loadEmployees(String filePath) {
        List<Employee> employees = new ArrayList<>();
        try {
            List<String[]> rows = readCSV(filePath);

            // Remove header row if present
            if (!rows.isEmpty() && rows.get(0)[0].toLowerCase().contains("employee")) {
                rows.remove(0);
            }

            for (String[] r : rows) {
                try {
                    if (r.length < 14) continue; // skip if not enough columns

                    // Defensive trim and fallback to zero if blank
                    int id = Integer.parseInt(r[0].trim());
                    String last = r[1].trim().replaceAll("^\"|\"$", "");
                    String first = r[2].trim().replaceAll("^\"|\"$", "");
                    String sss = r[3].trim();
                    String phil = r[4].trim();
                    String tin = r[5].trim();
                    String pagibig = r[6].trim();
                    String position = r[7].trim();
                    String supervisor = r[8].trim();

                    double salary = parseDoubleSafe(r[9]);
                    double rice = parseDoubleSafe(r[10]);
                    double phone = parseDoubleSafe(r[11]);
                    double clothing = parseDoubleSafe(r[12]);
                    double hourly = parseDoubleSafe(r[13]);

                    GovernmentDetails gov = new GovernmentDetails(sss, phil, tin, pagibig);
                    CompensationDetails comp = new CompensationDetails(salary, rice, phone, clothing, hourly);
                    Employee emp = new Employee(id, last, first, gov, comp);
                    emp.setPosition(position);
                    emp.setImmediateSupervisor(supervisor);

                    employees.add(emp);
                } catch (Exception inner) {
                    System.err.println("Skipping row due to error: " + Arrays.toString(r));
                    inner.printStackTrace();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading employee data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return employees;
    }

    private static double parseDoubleSafe(String value) {
        try {
            value = value.trim().replaceAll("\"", "");
            return value.isEmpty() ? 0 : Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public static void appendEmployee(List<String> row, String filepath) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(filepath, true))) {
            pw.println(String.join(",", row));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void saveEmployees(List<Employee> employees, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write header
            writer.write("Employee ID,Last Name,First Name,SSS,PhilHealth,TIN,Pag-IBIG,Position,Immediate Supervisor,Monthly Salary,Rice Subsidy,Phone Allowance,Clothing Allowance,Hourly Rate");
            writer.newLine();

            for (Employee emp : employees) {
                if (emp == null || emp.getGovernmentDetails() == null || emp.getCompensationDetails() == null) {
                    continue;
                }

                String[] row = new String[]{
                    String.valueOf(emp.getEmployeeId()),
                    emp.getLastName(),
                    emp.getFirstName(),
                    emp.getGovernmentDetails().getSssNumber(),
                    emp.getGovernmentDetails().getPhilHealthNumber(),
                    emp.getGovernmentDetails().getTinNumber(),
                    emp.getGovernmentDetails().getPagIbigNumber(),
                    emp.getPosition(),
                    emp.getImmediateSupervisor(),
                    String.valueOf(emp.getCompensationDetails().getMonthlySalary()),
                    String.valueOf(emp.getCompensationDetails().getRiceSubsidy()),
                    String.valueOf(emp.getCompensationDetails().getPhoneAllowance()),
                    String.valueOf(emp.getCompensationDetails().getClothingAllowance()),
                    String.valueOf(emp.getCompensationDetails().getHourlyRate())
                };

                // Quote and escape all values
                String quotedRow = Arrays.stream(row)
                        .map(s -> "\"" + (s == null ? "" : s.replace("\"", "\"\"")) + "\"")
                        .reduce((a, b) -> a + "," + b)
                        .orElse("");

                writer.write(quotedRow);
                writer.newLine();
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving employees: " + e.getMessage());
            e.printStackTrace();
        }

    }
    
}
    


    
   
