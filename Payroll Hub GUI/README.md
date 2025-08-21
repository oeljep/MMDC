# 🧾 MotorPH Employee Management System (with GUI and CSV Integration)

This Java Swing-based application allows MotorPH administrators to manage employee records, compute salaries, and store employee data using a CSV-based backend.

---

## ✅ Features Implemented

### 1. **Employee Dashboard (JTable)**
- Displays a list of employees using a `JTable` with the following fields:
  - Employee Number
  - Last Name
  - First Name
  - SSS Number
  - PhilHealth Number
  - TIN
  - Pag-IBIG Number
- Data is loaded from a CSV file on startup.

### 2. **View Employee Details**
- Allows users to select an employee from the table and click **"View Employee"**.
- Opens a new frame showing **full employee details**.
- User is prompted to select a **month** for salary computation.

### 3. **Salary Computation**
- After selecting the month and clicking **"Compute"**, the frame displays:
  - Employee information
  - Computed gross salary, deductions, and net pay
- Uses formulas based on attendance or standard monthly hours.

### 4. **New Employee Registration**
- Click **"New Employee"** to open a form.
- Input fields for new employee details.
- On submit:
  - Data is appended to the CSV file.
  - The employee list table is refreshed with the new entry.

## 💾 Data Storage
All employee records are stored in a file named: data\employee.csv
All login information are stored in: data\logins.csv
All attendance records are stored in: data\attendance.csv

Run the application:
Right-click Project → Run
Enter 'admin' as username
Enter 'user1' as password

