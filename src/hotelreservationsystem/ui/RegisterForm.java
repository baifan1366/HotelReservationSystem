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
import hotelreservationsystem.util.UUIDUtil;
import hotelreservationsystem.util.ValidationUtil;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class RegisterForm extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JButton registerButton;
    private JButton backButton;
    
    private CustomerDAO customerDAO;
    
    ImageIcon icons[] = {
        new ImageIcon(getClass().getResource("/image/circle-x.png")),
        new ImageIcon(getClass().getResource("/image/circle-check.png")),
        new ImageIcon(getClass().getResource("/image/user-round-plus.png"))
    };
    
    public RegisterForm() {
        customerDAO = new CustomerDAO();
        initComponents();
    }
    
    private void initComponents() {
        // Set frame properties
        setTitle("Hotel Reservation System - Register");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Customer Registration");
        titleLabel.setIcon(icons[2]);
        titleLabel.setHorizontalTextPosition(SwingConstants.CENTER);  // text at left
        titleLabel.setVerticalTextPosition(SwingConstants.BOTTOM);  // center vertically
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
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
        registerButton = new JButton("Proceed");
        registerButton.setIcon(icons[1]);
        registerButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        registerButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        registerButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        registerButton.setIconTextGap(10);
        registerButton.addActionListener(this);
        StyleConfig.applyStyle(registerButton);
        
        // Back button
        backButton = new JButton("Cancel");
        backButton.setIcon(icons[0]);
        backButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        backButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        backButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        backButton.setIconTextGap(10);
        backButton.addActionListener(this);
        StyleConfig.applyAccentStyle(backButton);
        
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
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        
        String fullName = firstName + " " + lastName;
        // Generate a unique userId
        String userId = UUIDUtil.generateShortUUID();
        
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
        
        if (!ValidationUtil.isValidPassword(password)) {
            JOptionPane.showMessageDialog(this,
                    "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one digit, and one special character.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create new customer with updated constructor
        Customer customer = new Customer(fullName, username, password, username + "@example.com", "N/A", "N/A");
        
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
