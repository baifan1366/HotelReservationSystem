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
    
    ImageIcon icons[] = {
        new ImageIcon(getClass().getResource("/image/log-in.png")),
        new ImageIcon(getClass().getResource("/image/hotel.png")),
        new ImageIcon(getClass().getResource("/image/user-round-plus.png"))
    };
    
    public LoginForm() {
        this.customerDAO = new CustomerDAO();
        this.adminDAO = new AdminDAO();
        initComponents();
    }
    
    private void initComponents() {
        // Set frame properties
        setTitle("Hotel Reservation System - Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        
        Image bgImg = new ImageIcon(getClass().getResource("/image/hotel-lobby.jpg")).getImage();
        BackgroundPanel myPanel = new BackgroundPanel(bgImg);

        myPanel.setLayout(new BorderLayout());
        
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/image/hotel.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        JLabel hotelLabel = new JLabel("Hotel Reservation System");
        hotelLabel.setIcon(scaledIcon);
        hotelLabel.setFont(new Font("Arial", Font.BOLD, 16));
        hotelLabel.setHorizontalTextPosition(SwingConstants.CENTER); 
        hotelLabel.setVerticalTextPosition(SwingConstants.BOTTOM);  
        hotelLabel.setHorizontalAlignment(SwingConstants.CENTER);  
        JLabel sloganLabel = new JLabel("Your stay, just a click away ~");
        sloganLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        sloganLabel.setHorizontalTextPosition(SwingConstants.CENTER); 
        sloganLabel.setHorizontalAlignment(SwingConstants.CENTER);  
        
        topPanel.add(hotelLabel);
        topPanel.add(sloganLabel);
        
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
        loginButton.setIcon(icons[0]);
        loginButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        loginButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        loginButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        loginButton.setIconTextGap(10);
        loginButton.addActionListener(this);

        // Register button
        registerButton = new JButton("Register");
        registerButton.setIcon(icons[2]);
        registerButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        registerButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        registerButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        registerButton.setIconTextGap(10);
        registerButton.addActionListener(this);
        
        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);       
        
        // Add panels to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Apply styling
        StyleConfig.applyStyle(this);
        StyleConfig.applyStyle(usernameField);
        StyleConfig.applyStyle(passwordField);
        StyleConfig.applyStyle(loginButton);
        StyleConfig.applyStyle(registerButton);
        
        // Add main panel to frame
        mainPanel.setOpaque(false);
        topPanel.setOpaque(false);
        formPanel.setOpaque(false);
        buttonPanel.setOpaque(false);
        myPanel.add(mainPanel, BorderLayout.CENTER);

        // Set background panel as content pane of this JFrame
        setContentPane(myPanel);
        setVisible(true); // make the current LoginForm visible
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
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Username and password cannot be empty", 
                "Login Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Try to login as admin using name (username) and password
        Admin admin = adminDAO.findAdminByNameAndPassword(username, password);
        if (admin != null) {
            openAdminDashboard(admin);
            dispose(); // Close login form
            return;
        }
        
        // Try to login as customer
        Customer customer = customerDAO.findCustomer(username, password);
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
