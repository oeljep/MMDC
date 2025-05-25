/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package payrollhubgui;

/**
 *
 * @author rowel
 */

import javax.swing.*;
import java.awt.event.*;
import payrollhubgui.Department.DepartmentGUI;
import payrollhubgui.dashboards.EmployeeDashboard;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EmployeeDashboard(1001, "Macky Arriesgado", 28, "Software Engineer", 800f);
        });
    }
}
    

