/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.ui;

import hotelreservationsystem.dao.BookingDAO;
import hotelreservationsystem.model.Booking;
import hotelreservationsystem.model.Customer;
import hotelreservationsystem.util.ValidationUtil;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JDialog;
import java.awt.Dimension;
import javax.swing.JComponent;
import java.awt.FlowLayout;

public class CustomerDashboard extends JFrame implements ActionListener {
    private Customer customer;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JButton bookRoomButton;
    private JButton viewBookingsButton;
    private JButton cancelBookingButton;
    private JButton logoutButton;
    private JButton viewRoomsButton;
    private JButton profileButton;
    private JTable bookingsTable;
    private DefaultTableModel bookingsTableModel;
    
    private BookingDAO bookingDAO;
    ImageIcon icons[] = {
        new ImageIcon(getClass().getResource("/image/log-out.png")),
        new ImageIcon(getClass().getResource("/image/circle-check.png")),
        new ImageIcon(getClass().getResource("/image/circle-x.png")),
        new ImageIcon(getClass().getResource("/image/file-plus-2.png")),
        new ImageIcon(getClass().getResource("/image/file-input.png")),
        new ImageIcon(getClass().getResource("/image/file-x-2.png")),
        new ImageIcon(getClass().getResource("/image/file-stack.png")),
        new ImageIcon(getClass().getResource("/image/square-user.png")),
    };
    public CustomerDashboard(Customer customer) {
        this.customer = customer;
        this.bookingDAO = new BookingDAO();
        initComponents();
    }
    
    private void initComponents() {
        // Set frame properties
        setTitle("Customer Dashboard - " + customer.getUserId());
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
                                                                //full name of user
        JLabel welcomeLabel = new JLabel("Welcome, " + customer.getFullName() + "!");
        StyleConfig.applyTitleStyle(welcomeLabel);
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        
        // Create navigation panel
        JPanel navPanel = new JPanel(new GridLayout(1, 7, 10, 0));
        
        // Book room button
        bookRoomButton = new JButton("Book a Room");
        bookRoomButton.addActionListener(this);
        bookRoomButton.setIcon(icons[3]);
        bookRoomButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        bookRoomButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        bookRoomButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        bookRoomButton.setIconTextGap(10);
        StyleConfig.applyStyle(bookRoomButton);
        
        // View bookings button
        viewBookingsButton = new JButton("My Bookings");
        viewBookingsButton.addActionListener(this);
        viewBookingsButton.setIcon(icons[4]);
        viewBookingsButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        viewBookingsButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        viewBookingsButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        viewBookingsButton.setIconTextGap(10);
        StyleConfig.applyStyle(viewBookingsButton);
        
        // Cancel booking button
        cancelBookingButton = new JButton("Cancel Booking");
        cancelBookingButton.addActionListener(this);
        cancelBookingButton.setIcon(icons[5]);
        cancelBookingButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        cancelBookingButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        cancelBookingButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        cancelBookingButton.setIconTextGap(10);
        StyleConfig.applyStyle(cancelBookingButton);
        
        // View all rooms button
        viewRoomsButton = new JButton("All Rooms");
        viewRoomsButton.addActionListener(this);
        viewRoomsButton.setIcon(icons[6]);
        viewRoomsButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        viewRoomsButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        viewRoomsButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        viewRoomsButton.setIconTextGap(10);
        StyleConfig.applyStyle(viewRoomsButton);
        
        // Profile button
        profileButton = new JButton("My Profile");
        profileButton.addActionListener(this);
        profileButton.setIcon(icons[7]);
        profileButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        profileButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        profileButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        profileButton.setIconTextGap(10);
        StyleConfig.applyStyle(profileButton);
        
        // Logout button
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        logoutButton.setIcon(icons[0]);
        logoutButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        logoutButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        logoutButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        logoutButton.setIconTextGap(10);
        StyleConfig.applyAccentStyle(logoutButton);
        
        navPanel.add(bookRoomButton);
        navPanel.add(viewBookingsButton);
        navPanel.add(cancelBookingButton);
        navPanel.add(viewRoomsButton);
        navPanel.add(profileButton);
        navPanel.add(logoutButton);
        
        // Create card panel for different views
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        // Create welcome panel (default view)
        JPanel welcomePanel = new JPanel(new BorderLayout());
        JComponent bookingsContent = createBookingsPanel(); 
        welcomePanel.add(bookingsContent, BorderLayout.CENTER);
        
        // Create bookings panel
        JPanel bookingsPanel = createBookingsPanel();
        
        // Add panels to card panel
        cardPanel.add(welcomePanel, "welcome");
        cardPanel.add(bookingsPanel, "bookings");
        
        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(navPanel, BorderLayout.SOUTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        
        // Apply styling
        StyleConfig.applyStyle(mainPanel);
        StyleConfig.applyStyle(headerPanel);
        StyleConfig.applyStyle(navPanel);
        StyleConfig.applyStyle(welcomePanel);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private JPanel createBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        // Create bookings table
        String[] columnNames = {"Booking ID", "Room Number", "Room Type", "Check-in Date", "Check-out Date", "Status"};
        bookingsTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };
        
        bookingsTable = new JTable(bookingsTableModel);
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        
        // Load bookings data
        loadBookingsData();
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void loadBookingsData() {
        // Clear existing data
        bookingsTableModel.setRowCount(0);
        
        // Get customer bookings
        Booking[] bookings = bookingDAO.getBookingsByCustomer(customer);
        
        // Add bookings to table
        for (Booking booking : bookings) {
            if (booking != null) {
                // Get payment status from payment object if available
                String status = "Not Paid";
                if (booking.getPayment() != null) {
                    status = booking.getPayment().getStatus();
                }
                
                Object[] rowData = {
                    booking.getBookingId(),
                    booking.getRoom().getRoomNumber(),
                    booking.getRoom().getType(),
                    booking.getCheckInDate(),
                    booking.getCheckOutDate(),
                    status
                };
                bookingsTableModel.addRow(rowData);
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookRoomButton) {
            openBookingForm();
        } else if (e.getSource() == viewBookingsButton) {
            loadBookingsData();
            cardLayout.show(cardPanel, "bookings");
        } else if (e.getSource() == cancelBookingButton) {
            openCancelBookingForm();
        } else if (e.getSource() == viewRoomsButton) {
            openRoomDetailsDialog();
        } else if (e.getSource() == profileButton) {
            showProfileDialog();
        } else if (e.getSource() == logoutButton) {
            logout();
        }
    }
    
    private void openBookingForm() {
        BookingForm bookingForm = new BookingForm(customer, this);
        bookingForm.setVisible(true);
    }
    
    private void openCancelBookingForm() {
        CancelBookingForm cancelForm = new CancelBookingForm(customer, this);
        cancelForm.setVisible(true);
    }
    
    private void logout() {
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
        this.dispose();
    }
    
    // Method to refresh the dashboard (called after booking/cancellation)
    public void refreshDashboard() {
        loadBookingsData();
        cardLayout.show(cardPanel, "bookings");
    }
    
    // Open dialog to show all rooms and their booking details
    private void openRoomDetailsDialog() {
        RoomDetailsDialog dialog = new RoomDetailsDialog(this);
        dialog.setVisible(true);
    }
    
    // Show customer profile dialog (editable)
    private void showProfileDialog() {
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(350, 80)); 
        JLabel titleLabel = new JLabel("Edit Profile");
        titleLabel.setIcon(icons[7]);
        titleLabel.setHorizontalTextPosition(SwingConstants.CENTER); 
        titleLabel.setVerticalTextPosition(SwingConstants.BOTTOM);  
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        StyleConfig.applyTitleStyle(titleLabel);
        titlePanel.add(titleLabel);
        StyleConfig.applyStyle(titlePanel);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        JTextField fullNameField = new JTextField(customer.getFullName());
        JTextField usernameField = new JTextField(customer.getUsername());
        JTextField emailField = new JTextField(customer.getEmail());
        JPasswordField passwordField = new JPasswordField(customer.getPassword());
        JTextField phoneField = new JTextField(customer.getPhone());
        JTextField addressField = new JTextField(customer.getAddress());

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Full Name:"));
        formPanel.add(fullNameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressField);

        // Bottom panel with OK and Cancel buttons
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton okButton = new JButton("Update");
        okButton.setIcon(icons[1]);
        okButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        okButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        okButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        okButton.setIconTextGap(10);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setIcon(icons[2]);
        cancelButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        cancelButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        cancelButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        cancelButton.setIconTextGap(10);
        
        StyleConfig.applyStyle(okButton);
        StyleConfig.applyAccentStyle(cancelButton);
        bottomPanel.add(okButton);
        bottomPanel.add(cancelButton);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Create dialog
        JDialog dialog = new JDialog(this, "Edit Profile", true);
        dialog.setContentPane(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        // Button actions
        okButton.addActionListener(e -> {
            // Validate form fields
            String username = usernameField.getText().trim();
            String fullName = fullNameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();
            
            // Validation checks
            if (username.isEmpty() || fullName.isEmpty() || email.isEmpty() || 
                    password.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, 
                        "All fields are required!", 
                        "Update Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Username validation
            if (!ValidationUtil.isValidUsername(username)) {
                JOptionPane.showMessageDialog(dialog, 
                        "Username should be 3-20 characters long and contain only letters, numbers, and underscores.", 
                        "Update Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Name validation
            if (!ValidationUtil.isValidName(fullName)) {
                JOptionPane.showMessageDialog(dialog, 
                        "Full name should contain only letters and be at least 2 characters long.", 
                        "Update Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Email validation
            if (!ValidationUtil.isValidEmail(email)) {
                JOptionPane.showMessageDialog(dialog, 
                        "Please enter a valid email address.", 
                        "Update Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Password validation
            if (!ValidationUtil.isValidPassword(password)) {
                JOptionPane.showMessageDialog(dialog,
                        "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one digit, and one special character.",
                        "Update Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // If all validations pass, update the customer object
            customer.setUsername(username);
            customer.setFullName(fullName);
            customer.setEmail(email);
            customer.setPassword(password);
            customer.setPhone(phone);
            customer.setAddress(address);

            // Save changes
            hotelreservationsystem.dao.CustomerDAO customerDAO = new hotelreservationsystem.dao.CustomerDAO();
            customerDAO.updateCustomer(customer);
            JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
            
            // Update the welcome label with new name
            JLabel welcomeLabel = (JLabel) ((JPanel) ((JPanel) getContentPane().getComponent(0)).getComponent(0)).getComponent(0);
            welcomeLabel.setText("Welcome, " + customer.getFullName() + "!");
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    // Getter for customer object (needed for the comment form)
    public Customer getCustomer() {
        return customer;
    }
}
