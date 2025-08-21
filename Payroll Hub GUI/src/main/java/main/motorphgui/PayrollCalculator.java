/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.motorphgui;

/**
 *
 * @author Macky
 */
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;


public class PayrollCalculator {

    private static final DecimalFormat df = new DecimalFormat("₱#,##0.00");
    private static double monthlyHours;
    
    public static String generatePayslip(int employeeId, String selectedMonth,float totalHoursWorked) {
    try {
        
        monthlyHours = 0; // reset before accumulating again
        
        List<String[]> employees = CSVHandler.readCSV("data/employee.csv");

        String[] empData = null;
        for (String[] row : employees) {
            if (String.valueOf(employeeId).equals(row[0].trim())) {
                empData = row;
                break;
            }
        }

        if (empData == null) return "Employee not found.";

        // Employee details
        String fullName = empData[2].trim() + " " + empData[1].trim();
        String position = empData[7].trim();
        String supervisor = empData[8].trim().isEmpty() ? "N/A" : empData[8].trim();
        double monthlySalary = Double.parseDouble(empData[9].trim());
        double rice = Double.parseDouble(empData[10].trim());
        double phone = Double.parseDouble(empData[11].trim());
        double clothing = Double.parseDouble(empData[12].trim());
        double hourlyRate = Double.parseDouble(empData[13].trim());

        double weeklyHours = 0, overtimeHrs = 0, overtimePay = 0;
        String month =  selectedMonth.trim();
        

        try (BufferedReader br = new BufferedReader(new FileReader("data/attendance.csv"))) {
            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue; // skip header
                }

                String[] row = line.split(",", -1);
                if (row.length < 13) {
                    continue;
                }

                if (row[0].trim().equals(String.valueOf(employeeId)) && getMonthFromDate(row[3].trim()).equalsIgnoreCase(month)){
                    try {
                        if (!row[7].isEmpty()) {
                            monthlyHours += Double.parseDouble(row[7].trim());   // Daily Hrs Worked
                        }
                        //  Sum all OT hours for the week
                            if (!row[9].trim().isEmpty()) {
                            try {
                                double parsedOT = Double.parseDouble(row[9].trim());
                                overtimeHrs += parsedOT;
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid OT for Employee" + employeeId + ": " + row[9]);
                            }
                        }

                        //  OT Pay
                        if (overtimePay == 0 && !row[12].isEmpty()) {
                            double parsedPay = Double.parseDouble(row[12].trim());
                            if (parsedPay > 0) {
                                overtimePay = parsedPay;
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing attendance: " + Arrays.toString(row));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading attendance.csv: " + e.getMessage());
        }

        double monthlyPay = monthlyHours * hourlyRate;
        double totalHours = monthlyHours + overtimeHrs;

        double sssDeduct = 0, philDeduct = 0, pagibigDeduct = 0, govTotal = 0;
        double grossTaxable = 0, tax = 0, grossDeductions = 0, netPay = 0;

        // Only compute deductions and pay if there was work
        if (monthlyHours > 0 || overtimeHrs > 0) {
            sssDeduct = getSSS(monthlySalary);
            philDeduct = getPhilHealth(monthlySalary);
            pagibigDeduct = getPagIbig(monthlySalary);
            govTotal = sssDeduct + philDeduct + pagibigDeduct;

            grossTaxable = monthlyPay - govTotal;
            tax = getWithholdingTax(monthlySalary);

            grossDeductions = govTotal + tax;
            netPay = monthlyPay - grossDeductions + rice + phone + clothing + overtimePay;
        }
        

        StringBuilder sb = new StringBuilder();

        sb.append("==========================================================================================\n");
        sb.append(String.format("%48s\n", "EMPLOYEE PAYSLIP"));
        sb.append("==========================================================================================\n");
        sb.append(String.format("Employee Name:     %-25s Position:    %-25s\n", fullName, position));
        sb.append(String.format("Employee ID:       %-25s Supervisor:  %-25s\n", employeeId, supervisor));
        sb.append(String.format("Month Coverage:    %-25s\n", selectedMonth + " " + Calendar.getInstance().get(Calendar.YEAR)));
        sb.append("==========================================================================================\n");
        sb.append(String.format("%-35s %-15s %-25s\n", "Description", "Hours", "Amount"));
        sb.append("==========================================================================================\n");

        sb.append(String.format("%-35s %-15s %-25s\n", "Regular Hours Worked:", String.format("%.2f", monthlyHours), df.format(monthlyPay)));
        sb.append(String.format("%-35s %-15s %-25s\n", "Overtime Hours:", String.format("%.2f", overtimeHrs), df.format(overtimePay)));
        sb.append(String.format("%-35s %-15s %-25s\n", "Total Hours Worked:", String.format("%.2f", totalHours), ""));

        sb.append("------------------------------------------------------------------------------------------\n");
        sb.append(String.format("%-35s %-15s %-25s\n", "Rice Subsidy:", "", df.format(rice)));
        sb.append(String.format("%-35s %-15s %-25s\n", "Phone Allowance:", "", df.format(phone)));
        sb.append(String.format("%-35s %-15s %-25s\n", "Clothing Allowance:", "", df.format(clothing)));

        sb.append("------------------------------------------------------------------------------------------\n");
        sb.append(String.format("%-35s %-15s %-25s\n", "SSS Deduction:", "", df.format(sssDeduct)));
        sb.append(String.format("%-35s %-15s %-25s\n", "PhilHealth:", "", df.format(philDeduct)));
        sb.append(String.format("%-35s %-15s %-25s\n", "Pag-IBIG:", "", df.format(pagibigDeduct)));

        sb.append("------------------------------------------------------------------------------------------\n");
        sb.append(String.format("%-35s %-15s %-25s\n", "Total Government Deductions:", "", df.format(govTotal)));
        sb.append(String.format("%-35s %-15s %-25s\n", "Monthly Gross Taxable Pay:", "", df.format(grossTaxable)));
        sb.append(String.format("%-35s %-15s %-25s\n", "Withholding Tax:", "", df.format(tax)));

        sb.append("==========================================================================================\n");
        sb.append(String.format("%-35s %-15s %-25s\n", "Total Deductions:", "", df.format(-grossDeductions)));
        sb.append(String.format("%-35s %-15s %-25s\n", "Net Pay:", "", df.format(netPay)));
        sb.append("==========================================================================================\n");

        return sb.toString();
    } catch (Exception e) {
        return "Error computing payslip: " + e.getMessage();
    }
}

    // Deduction Methods
    private static double getSSS(double salary) {
        double[] limits = {
            3249, 3749, 4249, 4749, 5249, 5749, 6249, 6749, 7249, 7749,
            8249, 8749, 9249, 9749, 10249, 10749, 11249, 11749, 12249,
            12749, 13249, 13749, 14249, 14749, 15249, 15749, 16249, 16749,
            17249, 17749, 18249, 18749, 19249, 19749, 20249, 20749, 21249,
            21749, 22249, 22749, 23249, 23749, 24249, 24749
        };
        double[] contributions = {
            135, 157.5, 180, 202.5, 225, 247.5, 270, 292.5, 315, 337.5,
            360, 382.5, 405, 427.5, 450, 472.5, 495, 517.5, 540, 562.5,
            585, 607.5, 630, 652.5, 675, 697.5, 720, 742.5, 765, 787.5,
            810, 832.5, 855, 877.5, 900, 922.5, 945, 967.5, 990, 1012.5,
            1035, 1057.5, 1080, 1102.5, 1125
        };
        for (int i = 0; i < limits.length; i++) {
            if (salary <= limits[i]) {
                return contributions[i];
            }
        }
        return 1125;
    }

    private static double getPhilHealth(double salary) {
        return Math.min(salary * 0.03, 1800) / 2;
    }

    private static double getPagIbig(double salary) {
        return Math.min(100, salary * 0.02);
    }

    private static double getWithholdingTax(double salary) {
        if (salary <= 20832) return 0;
        else if (salary <= 33332) return (salary - 20833) * 0.20;
        else if (salary <= 66666) return 2500 + (salary - 33333) * 0.25;
        else if (salary <= 166666) return 10833 + (salary - 66667) * 0.30;
        else if (salary <= 666666) return 40833 + (salary - 166667) * 0.32;
        else return 200833.33 + (salary - 666667) * 0.35;
    }

    public static List<String> getAvailableMonthsForEmployee(int employeeId) {
        return Arrays.asList("January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");
    }
    
    public static List<String> getAvailableYearsForEmployee(int employeeId) {
        Set<String> yearSet = new LinkedHashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/attendance.csv"))) {
            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] row = line.split(",", -1);
                if (row.length < 4) {
                    continue;
                }

                if (row[0].trim().equals(String.valueOf(employeeId))) {
                    String date = row[3].trim(); // expected format: MM/dd/yyyy
                    try {
                        java.text.SimpleDateFormat parser = new java.text.SimpleDateFormat("MM/dd/yyyy");
                        Date parsedDate = parser.parse(date);
                        java.text.SimpleDateFormat yearFormatter = new java.text.SimpleDateFormat("yyyy");
                        String year = yearFormatter.format(parsedDate);
                        yearSet.add(year);
                    } catch (Exception e) {
                        System.err.println("Invalid date: " + date);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read attendance.csv: " + e.getMessage());
        }

        return new ArrayList<>(yearSet);
    }
    private static String getMonthFromDate(String date) {
    try {
       
        java.text.SimpleDateFormat parser = new java.text.SimpleDateFormat("MM/dd/yyyy");
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("MMMM");
        Date parsedDate = parser.parse(date);
        return formatter.format(parsedDate); // Returns "July", "August", etc.  
    } catch (Exception e) {
        System.err.println("Error parsing date: " + date);
        return "";
    }
}

}
