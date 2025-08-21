/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.motorphgui;

/**
 *
 * @author Macky
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame implements ActionListener {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnCancel;
    private Login login;

    public LoginGUI() {
        setTitle("MotorPH Payroll Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 360);
        setLocationRelativeTo(null);
        setResizable(false);

        login = new Login("data/logins.csv");

        // Fonts & Colors
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 22);

        Color bgColor = new Color(245, 247, 251);
        Color accentColor = new Color(66, 133, 244);
        Color buttonGray = new Color(200, 200, 200);

        // === Main Content Panel ===
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(bgColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel lblTitle = new JLabel("MotorPH Payroll System", JLabel.CENTER);
        lblTitle.setFont(titleFont);
        lblTitle.setForeground(accentColor);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(lblTitle, gbc);

        // Username Label
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(labelFont);
        mainPanel.add(lblUsername, gbc);

        // Username Field
        gbc.gridx = 1;
        txtUsername = new JTextField(16);
        txtUsername.setFont(fieldFont);
        styleTextField(txtUsername);
        mainPanel.add(txtUsername, gbc);

        // Password Label
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(labelFont);
        mainPanel.add(lblPassword, gbc);

        // Password Field
        gbc.gridx = 1;
        txtPassword = new JPasswordField(16);
        txtPassword.setFont(fieldFont);
        styleTextField(txtPassword);
        mainPanel.add(txtPassword, gbc);

        // Button Panel
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(bgColor);

        btnLogin = new JButton("Login");
        btnCancel = new JButton("Cancel");
        styleButton(btnLogin, accentColor, Color.WHITE);
        styleButton(btnCancel, buttonGray, Color.BLACK);

        btnLogin.addActionListener(this);
        btnCancel.addActionListener(this);

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCancel);
        mainPanel.add(buttonPanel, gbc);

        // Final Setup
        getContentPane().setBackground(bgColor);
        getContentPane().add(mainPanel);
        getRootPane().setDefaultButton(btnLogin);
        setVisible(true);
    }

    private void styleTextField(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setBackground(Color.WHITE);
        field.setCaretColor(Color.BLACK);
    }

    private void styleButton(JButton button, Color bg, Color fg) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setPreferredSize(new Dimension(110, 36));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        // Optional: Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bg.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(bg);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            String user = txtUsername.getText().trim();
            String pass = String.valueOf(txtPassword.getPassword()).trim();

            if (login.validate(user, pass)) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                SwingUtilities.invokeLater(() -> new EmployeeTableFrame().setVisible(true));
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password!",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btnCancel) {
            System.exit(0);
        }
    }
}

