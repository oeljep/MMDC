/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payroll.hub;

/**
 *
 * @author rowel
 */

import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.nio.file.*;
import java.nio.file.StandardOpenOption;

public class PayCalculator {

    // Fields for employee allowances, hourly rate, name, and position
    private int riceSubsidy;
    private int phoneAllowance;
    private int clothingAllowance;
    private double hourlyRate;
    private String name; // Added field for employee name
    private String position; // Added field for employee position

    // Constructor for PayCalculator
    public PayCalculator(int riceSubsidy, int phoneAllowance, int clothingAllowance, double hourlyRate) {
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.hourlyRate = hourlyRate;
    }

    // Getters and setters for new fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    // File path for hourly rates and allowances
    private static final String EMPLOYEE_INFO = "C:\\Users\\rowel\\OneDrive\\Documents\\NetBeansProjects\\Payroll Hub\\src\\payroll\\hub\\databases\\hourlyrate_allowances.csv";
    private static final Map<String, PayCalculator> employeeMapRates = new HashMap<>();
    private static final String TIMEKEEPING_FILE = "C:\\Users\\rowel\\OneDrive\\Documents\\NetBeansProjects\\Payroll Hub\\src\\payroll\\hub\\databases\\employeeinfo_timekeeping.csv";
    private static final int GRACE_PERIOD_MINUTES = 10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Hardcoded file path for timekeeping data
        String timekeepingFilePath = "C:\\Users\\rowel\\OneDrive\\Documents\\NetBeansProjects\\Payroll Hub\\src\\payroll\\hub\\databases\\employeeinfo_timekeeping.csv";

        boolean Continue = true; // to continue the program

    while (Continue) {

        // Prompt user for input
        System.out.print("Enter Employee ID: ");
        String employeeID = scanner.nextLine();

        System.out.println("Choose a pay period from 2024-06-03 to 2024-12-31");
        System.out.print("Enter start date (yyyy-MM-dd): ");
        String startDateInput = scanner.nextLine();
        System.out.print("Enter end date (yyyy-MM-dd): ");
        String endDateInput = scanner.nextLine();

        try {
            // Parse the input dates
            LocalDate startDate = LocalDate.parse(startDateInput);
            LocalDate endDate = LocalDate.parse(endDateInput);

            // Load employee info if not already loaded
            loadEmployeeInfo();

            // Validate and compute payroll for the employee
            if (employeeMapRates.containsKey(employeeID)) {
                computeAndDisplayPayroll(employeeID, startDate, endDate);
            } else {
                System.out.println("Employee ID not found.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Error parsing date or time data: " + e.getMessage());
        }

        // Prompt user to continue or exit
        System.out.print("\nWould you like to compute payroll for another employee? (yes/no): ");
        String userChoice = scanner.nextLine().trim().toLowerCase();

        if (userChoice.equals("no")) {
            Continue = false; // Exit the loop
            System.out.println("Thank you for using the MotorPh Payroll Hub.");
        }
    }
    scanner.close(); // Close scanner to free resources

    }

    // Load employee info from CSV into HashMap
    private static void loadEmployeeInfo() {
        try (BufferedReader br = new BufferedReader(new FileReader(EMPLOYEE_INFO))) {
            String line;
            boolean isHeaderRow = true; // Flag to skip header row

            while ((line = br.readLine()) != null) {
                if (isHeaderRow) {
                    isHeaderRow = false; // Skip the header row
                    continue;
                }

                String[] values = line.split(",");
                if (values.length >= 7) { // Ensure all columns exist
                    String employeeID = values[0].trim();
                    String employeeName = values[1].trim();
                    
                    try {
                        int riceSubsidy = Integer.parseInt(values[2].trim());
                        int phoneAllowance = Integer.parseInt(values[3].trim());
                        int clothingAllowance = Integer.parseInt(values[4].trim());
                        double hourlyRate = Double.parseDouble(values[5].trim());
                        String position = values[6].trim();

                        // Store all data in PayCalculator object
                        PayCalculator empInfo = new PayCalculator(riceSubsidy, phoneAllowance, clothingAllowance, hourlyRate);
                        empInfo.setName(employeeName);
                        empInfo.setPosition(position);
                        employeeMapRates.put(employeeID, empInfo);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing number in row: " + line);
                        continue; // Skip to the next row
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }
        private static double calculateWithholdingTax(double taxableIncome) {
        if (taxableIncome <= 20832) {
            return 0;
        } else if (taxableIncome <= 33333) {
            return (taxableIncome - 20833) * 0.20;
        } else if (taxableIncome <= 666667) {
            return 2500 + (taxableIncome - 33333) * 0.25;
        } else if (taxableIncome <= 166667) {
            return 10833 + (taxableIncome - 666667) * 0.30;
        } else if (taxableIncome <= 666667) {
            return 40833.33 + (taxableIncome - 166667) * 0.32;
        } else {
            return 200833.33 + (taxableIncome - 666667) * 0.35;
        }
    }
        
  private static double calculatePagibig(double grossPay) {
        double rate = (grossPay > 1500) ? 0.02 : 0.01;
        return Math.min(grossPay * rate, 100); // Maximum of 100
    }
  
 private static double calculatePhilhealth(double grossPay) {
        double rate = 0.03;
        double contribution = grossPay * rate;
        return Math.min(contribution / 2, 900); // Employee pays half, max of 900
    }
  
 private static double calculateSSS(double grossPay) {
        if (grossPay < 3250) {
            return 135.0;
        } else if (grossPay <= 3749.99) {
            return 157.5;
        } else if (grossPay <= 4249.99) {
            return 180.0;
        } else if (grossPay <= 4749.99) {
            return 202.5;
        } else if (grossPay <= 5249.99) {
            return 225.0;
        } else if (grossPay <= 5749.99) {
            return 247.5;
        } else if (grossPay <= 6249.99) {
            return 270.0;
        } else if (grossPay <= 6749.99) {
            return 292.5;
        } else if (grossPay <= 7249.99) {
            return 315.0;
        } else if (grossPay <= 7749.99) {
            return 337.5;
        } else if (grossPay <= 8249.99) {
            return 360.0;
        } else if (grossPay <= 8749.99) {
            return 382.5;
        } else if (grossPay <= 9249.99) {
            return 405.0;
        } else if (grossPay <= 9749.99) {
            return 427.5;
        } else if (grossPay <= 10249.99) {
            return 450.0;
        } else if (grossPay <= 10749.99) {
            return 472.5;
        } else if (grossPay <= 11249.99) {
            return 495.0;
        } else if (grossPay <= 11749.99) {
            return 517.5;
        } else if (grossPay <= 12249.99) {
            return 540.0;
        } else if (grossPay <= 12749.99) {
            return 562.5;
        } else if (grossPay <= 13249.99) {
            return 585.0;
        } else if (grossPay <= 13749.99) {
            return 607.5;
        } else if (grossPay <= 14249.99) {
            return 630.0;
        } else if (grossPay <= 14749.99) {
            return 652.5;
        } else if (grossPay <= 15249.99) {
            return 675.0;
        } else if (grossPay <= 15749.99) {
            return 697.5;
        } else if (grossPay <= 16249.99) {
            return 720.0;
        } else if (grossPay <= 16749.99) {
            return 742.5;
        } else if (grossPay <= 17249.99) {
            return 765.0;
        } else if (grossPay <= 17749.99) {
            return 787.5;
        } else if (grossPay <= 18249.99) {
            return 810.0;
        } else if (grossPay <= 18749.99) {
            return 832.5;
        } else if (grossPay <= 19249.99) {
            return 855.0;
        } else if (grossPay <= 19749.99) {
            return 877.5;
        } else if (grossPay <= 20249.99) {
            return 900.0;
        } else if (grossPay <= 20749.99) {
            return 922.5;
        } else if (grossPay <= 21249.99) {
            return 945.0;
        } else if (grossPay <= 21749.99) {
            return 967.5;
        } else if (grossPay <= 22249.99) {
            return 990.0;
        } else if (grossPay <= 22749.99) {
            return 1012.5;
        } else if (grossPay <= 23249.99) {
            return 1035.0;
        } else if (grossPay <= 23749.99) {
            return 1057.5;
        } else if (grossPay <= 24249.99) {
            return 1080.0;
        } else if (grossPay <= 24749.99) {
            return 1102.5;
        } else {
            return 1125.0; // For gross pay of 24750 and above
        }
    }

    // Compute and display payroll details
    private static void computeAndDisplayPayroll(String employeeID, LocalDate startDate, LocalDate endDate) {
        String csvFilePath = "C:\\Users\\USER\\Documents\\NetBeansProjects\\MO-IT101-Group1\\src\\payroll\\hub\\databases\\MotorPHPayslip.csv";
   
        PayCalculator empInfo = employeeMapRates.get(employeeID);
        if (empInfo == null) {
            System.out.println("Employee ID not found in employee data.");
            return;
        }
        double hourlyRate = empInfo.hourlyRate;
        int riceSubsidy = empInfo.riceSubsidy;
        int phoneAllowance = empInfo.phoneAllowance;
        int clothingAllowance = empInfo.clothingAllowance;
        String employeeName = empInfo.getName();
        String position = empInfo.getPosition();
        double totalWorkedHours = 0;
        double totalOvertimeHours = 0;
        double totalTardinessHours = 0;
        int daysLate = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(TIMEKEEPING_FILE))) {
            String line;
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
             boolean isHeaderRow = true;

            while ((line = br.readLine()) != null) {
                 if (isHeaderRow) {
                    isHeaderRow = false; // Skip the header row
                    continue;
                }
                String[] data = line.split(",");
                if (data.length < 6) continue;

                String id = data[0].trim();
                LocalDate recordDate = LocalDate.parse(data[3].trim(), dateFormatter);

                if (id.equalsIgnoreCase(employeeID) && !recordDate.isBefore(startDate) && !recordDate.isAfter(endDate)) {
                    LocalTime scheduledTimeIn = LocalTime.parse("08:00", timeFormatter); // Assuming 08:00 AM is the scheduled time-in
                    LocalTime timeIn = LocalTime.parse(data[4].trim(), timeFormatter);
                    LocalTime timeOut = LocalTime.parse(data[5].trim(), timeFormatter);

                    Duration workDuration = Duration.between(timeIn, timeOut);
                    double dailyWorkedHours = workDuration.toMinutes() / 60.0;

                    //Tardiness Calculation with Grace Period
                    Duration tardiness = Duration.between(scheduledTimeIn, timeIn);
                    if (tardiness.toMinutes() > GRACE_PERIOD_MINUTES && timeIn.isAfter(scheduledTimeIn)) {
                        totalTardinessHours += (tardiness.toMinutes() - GRACE_PERIOD_MINUTES) / 60.0;
                        daysLate++;
                    }

                    // Overtime Calculation
                    double regularHours = Math.min(dailyWorkedHours, 8);
                    double overtimeHours = Math.max(dailyWorkedHours - 8, 0);

                    totalWorkedHours += regularHours;
                    totalOvertimeHours += overtimeHours;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading timekeeping file: " + e.getMessage());
            return;
        }

        double overtimeRate = 1.25; // 25% additional rate for OT
        double regularPay = totalWorkedHours * hourlyRate;
        double overtimePay = totalOvertimeHours * hourlyRate * overtimeRate;
        double totalAllowances = riceSubsidy + phoneAllowance + clothingAllowance;
        double grossPay = regularPay + overtimePay + totalAllowances;

        // Government Deductions (applied only on the first cutoff)
        boolean isFirstCutoff = startDate.getDayOfMonth() <= 15;
        double sssDeduction = isFirstCutoff ? calculateSSS(grossPay) : 0;
        double philhealthDeduction = isFirstCutoff ? calculatePhilhealth(grossPay) : 0;
        double pagibigDeduction = isFirstCutoff ? calculatePagibig(grossPay) : 0;

        double taxableIncome = grossPay - (sssDeduction + philhealthDeduction + pagibigDeduction);
        double withholdingTax = calculateWithholdingTax(taxableIncome);
        double totalDeductions = sssDeduction + philhealthDeduction + pagibigDeduction + withholdingTax;
        double netPay = grossPay - totalDeductions;
        
        // Divide the withholding tax by 2 for the cut-offs
        double adjustedWithholdingTax = withholdingTax / 2.0;

        //Tardiness Deduction
        double tardinessDeduction = totalTardinessHours * hourlyRate;

        // Format output for console and CSV
    String payslip = String.format(
        "\n======================================================\n"
        + "               MOTORPH PAYROLL STATEMENT              \n"
        + "======================================================\n"
        + "  Employee: %-36s\n"
        + "  ID: %-41s\n"
        + "  Position: %-37s\n"
        + "  Period: %-38s\n"
        + "  Hourly Rate: P%.2f\n"
        + "  Days Late: %d\n"
        + "------------------------------------------------------\n"
        + "  Description             Hours          Amount\n"
        + "------------------------------------------------------\n"
        + "  Basic Pay               %7.2f       P%11.2f\n"
        + "  Overtime (1.25x rate)   %7.2f       P%11.2f\n"
        + "  Tardiness               -%6.2f      -P%11.2f\n"
        + "  Rice Subsidy            %7s       P%11.2f\n"
        + "  Phone Allowance         %7s       P%11.2f\n"
        + "  Clothing Allowance      %7s       P%11.2f\n"
        + "------------------------------------------------------\n"
        + "  GROSS PAY                            P%12.2f\n"
        + "------------------------------------------------------\n"
        + "  SSS Deduction                        -P%11.2f\n"
        + "  PhilHealth                           -P%11.2f\n"
        + "  Pag-IBIG                             -P%11.2f\n"
        + "  Withholding Tax                      -P%11.2f\n"
        + "------------------------------------------------------\n"
        + "  TOTAL DEDUCTIONS                   -P%13.2f\n"
        + "======================================================\n"
        + "  NET PAY:                            P%13.2f\n"
        + "======================================================\n",
        // Arguments
        employeeName,
        employeeID,
        position,
        startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " to " + endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        hourlyRate, // %.2f
        daysLate, // %d
        totalWorkedHours, regularPay,
        totalOvertimeHours, overtimePay,
        totalTardinessHours, tardinessDeduction,
        "", (double) riceSubsidy,
        "", (double) phoneAllowance,
        "", (double) clothingAllowance,
        grossPay,
        sssDeduction,
        philhealthDeduction,
        pagibigDeduction,
        adjustedWithholdingTax,
        totalDeductions,
        netPay
    );

// Print to console
System.out.println(payslip);


    // CSV data
    List<String> csvData = Arrays.asList(
        employeeID, employeeName, position,
        startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " to " + endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        String.valueOf(totalWorkedHours), String.valueOf(totalTardinessHours), String.valueOf(-tardinessDeduction), String.valueOf(daysLate),
        String.valueOf(hourlyRate), String.valueOf(regularPay), String.valueOf(overtimePay),
        String.valueOf(riceSubsidy), String.valueOf(phoneAllowance), String.valueOf(clothingAllowance), String.valueOf(totalAllowances),
        String.valueOf(grossPay), String.valueOf(sssDeduction), String.valueOf(philhealthDeduction), String.valueOf(pagibigDeduction), String.valueOf(withholdingTax),
        String.valueOf(totalDeductions), String.valueOf(netPay)
    );

        String csvFilePathLocal = "C:\\Users\\rowel\\OneDrive\\Documents\\NetBeansProjects\\Payroll Hub\\src\\payroll\\hub\\MotorPHPayslip.csv";

    // Write to CSV file
    try {
        Files.write(
            Paths.get(csvFilePathLocal),
            (String.join(",", csvData) + System.lineSeparator()).getBytes(),
            StandardOpenOption.CREATE, StandardOpenOption.APPEND
        );
        System.out.println("Payroll data saved to " + csvFilePathLocal);
    } catch (IOException e) {
        System.err.println("Error writing to CSV file: " + e.getMessage());
    }
    }
}