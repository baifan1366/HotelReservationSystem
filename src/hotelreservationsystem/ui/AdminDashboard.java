/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.ui;

import hotelreservationsystem.HotelReservationSystem;
import hotelreservationsystem.dao.AdminDAO;
import hotelreservationsystem.dao.BookingDAO;
import hotelreservationsystem.dao.FileManager;
import hotelreservationsystem.dao.RoomDAO;
import hotelreservationsystem.model.Admin;
import hotelreservationsystem.model.Booking;
import hotelreservationsystem.model.Room;
import hotelreservationsystem.model.User;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AdminDashboard extends JFrame implements ActionListener {
    private Admin admin;
    private JTabbedPane tabbedPane;
    private JButton addRoomButton;
    private JButton addAdminButton;
    private JButton generateReportButton;
    private JButton logoutButton;
    private JTable roomsTable;
    private DefaultTableModel roomsTableModel;
    private JTable bookingsTable;
    private DefaultTableModel bookingsTableModel;
    
    private RoomDAO roomDAO;
    private BookingDAO bookingDAO;
    
    public AdminDashboard(Admin admin) {
        this.admin = admin;
        this.roomDAO = new RoomDAO();
        this.bookingDAO = new BookingDAO();
        initComponents();
    }
    
    private void initComponents() {
        // Set frame properties
        setTitle("Admin Dashboard - " + admin.getName());
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Administrator Dashboard");
        StyleConfig.applyTitleStyle(welcomeLabel);
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        
        // Add room button
        addRoomButton = new JButton("Add New Room");
        addRoomButton.addActionListener(this);
        StyleConfig.applyStyle(addRoomButton);
        
        // Add admin button
        addAdminButton = new JButton("Add New Admin");
        addAdminButton.addActionListener(this);
        StyleConfig.applyStyle(addAdminButton);
        
        // Generate report button
        generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(this);
        StyleConfig.applyStyle(generateReportButton);
        
        // Logout button
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        StyleConfig.applyAccentStyle(logoutButton);
        
        buttonPanel.add(addRoomButton);
        buttonPanel.add(addAdminButton);
        buttonPanel.add(generateReportButton);
        buttonPanel.add(logoutButton);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Add rooms panel
        JPanel roomsPanel = createRoomsPanel();
        tabbedPane.addTab("Rooms", roomsPanel);
        
        // Add bookings panel
        JPanel bookingsPanel = createBookingsPanel();
        tabbedPane.addTab("Bookings", bookingsPanel);
        
        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Apply styling
        StyleConfig.applyStyle(mainPanel);
        StyleConfig.applyStyle(headerPanel);
        StyleConfig.applyStyle(buttonPanel);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private JPanel createRoomsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        // Create rooms table
        String[] columnNames = {"Room Number", "Room Type", "Price per Night", "Available"};
        roomsTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };
        
        roomsTable = new JTable(roomsTableModel);
        JScrollPane scrollPane = new JScrollPane(roomsTable);
        
        // Load rooms data
        loadRoomsData();
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        // Create bookings table
        String[] columnNames = {"Booking ID", "Customer", "Room Number", "Check-in Date", "Check-out Date", "Payment Status"};
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
    
    private void loadRoomsData() {
        // Clear existing data
        roomsTableModel.setRowCount(0);
        
        // Get all rooms
        Room[] rooms = HotelReservationSystem.getRooms();
        int roomCount = HotelReservationSystem.getRoomCount();
        
        // Add rooms to table
        for (int i = 0; i < roomCount; i++) {
            Room room = rooms[i];
            if (room != null) {
                Object[] rowData = {
                    room.getRoomNumber(),
                    room.getType(),
                    room.getPricePerNight(),
                    room.isAvailable() ? "Yes" : "No"
                };
                roomsTableModel.addRow(rowData);
            }
        }
    }
    
    private void loadBookingsData() {
        // Clear existing data
        bookingsTableModel.setRowCount(0);
        
        // Get all bookings
        Booking[] bookings = bookingDAO.getAllActiveBookings();
        
        // Add bookings to table
        for (Booking booking : bookings) {
            if (booking != null) {
                Object[] rowData = {
                    booking.getBookingId(),
                    booking.getCustomer().getName(),
                    booking.getRoom().getRoomNumber(),
                    booking.getCheckInDate(),
                    booking.getCheckOutDate(),
                    booking.isPaid() ? "Paid" : "Pending Payment"
                };
                bookingsTableModel.addRow(rowData);
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addRoomButton) {
            addNewRoom();
        } else if (e.getSource() == addAdminButton) {
            addNewAdmin();
        } else if (e.getSource() == generateReportButton) {
            generateReport();
        } else if (e.getSource() == logoutButton) {
            logout();
        }
    }
    
    private void addNewRoom() {
        // Simple dialog to add a new room
        try {
            String roomNumberStr = JOptionPane.showInputDialog(this, "Enter Room Number:");
            if (roomNumberStr == null || roomNumberStr.isEmpty()) return;
            
            int roomNumber = Integer.parseInt(roomNumberStr);
            
            String roomType = JOptionPane.showInputDialog(this, "Enter Room Type (Single/Double/Suite):");
            if (roomType == null || roomType.isEmpty()) return;
            
            String priceStr = JOptionPane.showInputDialog(this, "Enter Price Per Night:");
            if (priceStr == null || priceStr.isEmpty()) return;
            
            double price = Double.parseDouble(priceStr);
            
            // Create and add new room
            Room room = new Room(generateRoomId(), roomNumber, roomType, price, true);
            boolean success = roomDAO.addRoom(room);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Room added successfully!");
                loadRoomsData(); // Refresh rooms table
            } else {
                JOptionPane.showMessageDialog(this, "Room number already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addNewAdmin() {
        // Create a dialog to add a new admin
        JDialog dialog = new JDialog(this, "Add New Admin", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create input panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        
        // User ID field
        JLabel userIdLabel = new JLabel("User ID:");
        JTextField userIdField = new JTextField();
        
        // Name field
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        
        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        
        // Email field
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        
        // Admin ID field
        JLabel adminIdLabel = new JLabel("Admin ID:");
        JTextField adminIdField = new JTextField();
        
        // Role field
        JLabel roleLabel = new JLabel("Role:");
        JTextField roleField = new JTextField();
        
        // Add components to input panel
        inputPanel.add(userIdLabel);
        inputPanel.add(userIdField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);
        inputPanel.add(adminIdLabel);
        inputPanel.add(adminIdField);
        inputPanel.add(roleLabel);
        inputPanel.add(roleField);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        // Add action listeners
        cancelButton.addActionListener(e -> dialog.dispose());
        
        saveButton.addActionListener(e -> {
            try {
                // Validate input
                String userId = userIdField.getText().trim();
                String name = nameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String email = emailField.getText().trim();
                String adminId = adminIdField.getText().trim();
                String role = roleField.getText().trim();
                
                if (userId.isEmpty() || name.isEmpty() || password.isEmpty() || 
                    email.isEmpty() || adminId.isEmpty() || role.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "All fields are required!", 
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Check if admin ID already exists
                AdminDAO adminDAO = new AdminDAO();
                if (adminDAO.findAdminByUserId(userId) != null) {
                    JOptionPane.showMessageDialog(dialog, "User ID already exists!", 
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Create new admin
                Admin newAdmin = new Admin(userId, name, password, email, adminId, role);
                
                // Add admin to the system
                User[] users = HotelReservationSystem.getUsers();
                int userCount = HotelReservationSystem.getUserCount();
                
                if (userCount < users.length) {
                    users[userCount] = newAdmin;
                    HotelReservationSystem.setUserCount(userCount + 1);
                    
                    // Save users to file
                    new FileManager().saveUsers(users);
                    
                    JOptionPane.showMessageDialog(dialog, "Admin added successfully!");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "User database is full!", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error adding admin: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Add panels to dialog
        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // Apply styling
        StyleConfig.applyStyle(inputPanel);
        StyleConfig.applyStyle(buttonPanel);
        StyleConfig.applyStyle(saveButton);
        StyleConfig.applyAccentStyle(cancelButton);
        
        // Show dialog
        dialog.setVisible(true);
    }
    
    // Helper method to generate a unique room ID
    private int generateRoomId() {
        Room[] rooms = HotelReservationSystem.getRooms();
        int maxId = 0;
        
        for (Room room : rooms) {
            if (room != null && room.getRoomId() > maxId) {
                maxId = room.getRoomId();
            }
        }
        
        return maxId + 1;
    }
    
    private void generateReport() {
        ReportPanel reportPanel = new ReportPanel();
        reportPanel.setVisible(true);
    }
    
    private void logout() {
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
        this.dispose();
    }
    
    // Method to refresh the dashboard
    public void refreshDashboard() {
        loadRoomsData();
        loadBookingsData();
    }
}
