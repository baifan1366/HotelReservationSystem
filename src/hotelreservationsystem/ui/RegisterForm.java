/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.ui;

import hotelreservationsystem.dao.CustomerDAO;
import hotelreservationsystem.model.Customer;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterForm extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JButton registerButton;
    private JButton backButton;
    
    private CustomerDAO customerDAO;
    
    public RegisterForm() {
        customerDAO = new CustomerDAO();
        initComponents();
    }
    
    private void initComponents() {
        // Set frame properties
        setTitle("Hotel Reservation System - Register");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Customer Registration");
        StyleConfig.applyTitleStyle(titleLabel);
        titlePanel.add(titleLabel);
        StyleConfig.applyStyle(titlePanel);
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        
        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        StyleConfig.applyStyle(usernameLabel);
        StyleConfig.applyStyle(usernameField);
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        
        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        StyleConfig.applyStyle(passwordLabel);
        StyleConfig.applyStyle(passwordField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        
        // Confirm password field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField();
        StyleConfig.applyStyle(confirmPasswordLabel);
        StyleConfig.applyStyle(confirmPasswordField);
        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordField);
        
        // First name field
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField();
        StyleConfig.applyStyle(firstNameLabel);
        StyleConfig.applyStyle(firstNameField);
        formPanel.add(firstNameLabel);
        formPanel.add(firstNameField);
        
        // Last name field
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField();
        StyleConfig.applyStyle(lastNameLabel);
        StyleConfig.applyStyle(lastNameField);
        formPanel.add(lastNameLabel);
        formPanel.add(lastNameField);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        // Register button
        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        StyleConfig.applyStyle(registerButton);
        
        // Back button
        backButton = new JButton("Back to Login");
        backButton.addActionListener(this);
        StyleConfig.applyStyle(backButton);
        
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        
        // Add panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Apply styling
        StyleConfig.applyStyle(mainPanel);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            handleRegistration();
        } else if (e.getSource() == backButton) {
            backToLogin();
        }
    }
    
    private void handleRegistration() {
        // Get form data
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        
        // Validate form data
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || 
                firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "All fields are required!", 
                    "Registration Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, 
                    "Passwords do not match!", 
                    "Registration Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create new customer
        Customer customer = new Customer(username, firstName + " " + lastName, password, username + "@example.com", "N/A", "N/A");
        
        // Register customer
        boolean success = customerDAO.registerCustomer(customer);
        
        if (success) {
            JOptionPane.showMessageDialog(this, 
                    "Registration successful! You can now login.", 
                    "Registration Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            backToLogin();
        } else {
            JOptionPane.showMessageDialog(this, 
                    "Username already exists. Please choose another username.", 
                    "Registration Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void backToLogin() {
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
        this.dispose();
    }
}
