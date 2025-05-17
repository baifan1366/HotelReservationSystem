/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.ui;

/**
 *
 * @author user
 */

import hotelreservationsystem.dao.CustomerDAO;
import hotelreservationsystem.dao.AdminDAO;
import hotelreservationsystem.model.Customer;
import hotelreservationsystem.model.Admin;
import hotelreservationsystem.model.User;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import hotelreservationsystem.HotelReservationSystem;

public class LoginForm extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    
    private CustomerDAO customerDAO;
    private AdminDAO adminDAO;
    
    public LoginForm() {
        this.customerDAO = new CustomerDAO();
        this.adminDAO = new AdminDAO();
        initComponents();
    }
    
    private void initComponents() {
        // Set frame properties
        setTitle("Hotel Reservation System - Login");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        
        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        
        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        // Login button
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        
        // Register button
        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        
        // Add panels to main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Apply styling
        StyleConfig.applyStyle(this);
        StyleConfig.applyStyle(usernameField);
        StyleConfig.applyStyle(passwordField);
        StyleConfig.applyStyle(loginButton);
        StyleConfig.applyStyle(registerButton);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            handleLogin();
        } else if (e.getSource() == registerButton) {
            openRegistrationForm();
        }
    }
    
    private void handleLogin() {
        String userId = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        if (userId.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Username and password cannot be empty", 
                "Login Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Try to login as admin
        Admin admin = adminDAO.findAdmin(userId, password);
        if (admin != null) {
            openAdminDashboard(admin);
            dispose(); // Close login form
            return;
        }
        
        // Try to login as customer
        Customer customer = customerDAO.findCustomer(userId, password);
        if (customer != null) {
            openCustomerDashboard(customer);
            dispose(); // Close login form
            return;
        }
        
        // If we get here, login failed
        JOptionPane.showMessageDialog(this, 
            "Invalid username or password", 
            "Login Error", 
            JOptionPane.ERROR_MESSAGE);
    }
    
    private void openRegistrationForm() {
        RegisterForm registerForm = new RegisterForm();
        registerForm.setVisible(true);
        this.dispose();
    }
    
    private void openAdminDashboard(Admin admin) {
        AdminDashboard dashboard = new AdminDashboard(admin);
        dashboard.setVisible(true);
    }
    
    private void openCustomerDashboard(Customer customer) {
        CustomerDashboard dashboard = new CustomerDashboard(customer);
        dashboard.setVisible(true);
    }
}
