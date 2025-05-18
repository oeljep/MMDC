/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gradecalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MilestoneCalculator extends JFrame {

    private JTextField milestone1Field;
    private JTextField milestone2Field;
    private JTextField terminalAssessmentField;
    private JLabel resultLabel;

    public MilestoneCalculator() {
        setTitle("Milestone Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(null); // center on screen

        // Use GridLayout for simplicity
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Milestone 1 (Max 25):"));
        milestone1Field = new JTextField();
        panel.add(milestone1Field);

        panel.add(new JLabel("Milestone 2 (Max 40):"));
        milestone2Field = new JTextField();
        panel.add(milestone2Field);

        panel.add(new JLabel("Terminal Assessment (Max 35):"));
        terminalAssessmentField = new JTextField();
        panel.add(terminalAssessmentField);

        JButton calculateButton = new JButton("Calculate Grade");
        panel.add(calculateButton);

        resultLabel = new JLabel("");
        panel.add(resultLabel);

        add(panel);

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateFinalGrade();
            }
        });

        setVisible(true);
    }

    private void calculateFinalGrade() {
        try {
            double milestone1 = Double.parseDouble(milestone1Field.getText());
            double milestone2 = Double.parseDouble(milestone2Field.getText());
            double terminalAssessment = Double.parseDouble(terminalAssessmentField.getText());

            if (milestone1 < 0 || milestone1 > 25)
                throw new IllegalArgumentException("Milestone 1 must be between 0 and 25.");
            if (milestone2 < 0 || milestone2 > 40)
                throw new IllegalArgumentException("Milestone 2 must be between 0 and 40.");
            if (terminalAssessment < 0 || terminalAssessment > 35)
                throw new IllegalArgumentException("Terminal Assessment must be between 0 and 35.");

            double finalGrade = milestone1 + milestone2 + terminalAssessment;
            resultLabel.setText("Final Grade: " + finalGrade + "/100");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Input Out of Range", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new MilestoneCalculator();
    }
}
