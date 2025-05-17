/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.ui;

import hotelreservationsystem.HotelReservationSystem;
import hotelreservationsystem.dao.BookingDAO;
import hotelreservationsystem.dao.RoomDAO;
import hotelreservationsystem.model.Admin;
import hotelreservationsystem.model.Booking;
import hotelreservationsystem.model.Room;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AdminDashboard extends JFrame implements ActionListener {
    private Admin admin;
    private JTabbedPane tabbedPane;
    private JButton addRoomButton;
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
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        
        // Add room button
        addRoomButton = new JButton("Add New Room");
        addRoomButton.addActionListener(this);
        StyleConfig.applyStyle(addRoomButton);
        
        // Generate report button
        generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(this);
        StyleConfig.applyStyle(generateReportButton);
        
        // Logout button
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        StyleConfig.applyAccentStyle(logoutButton);
        
        buttonPanel.add(addRoomButton);
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
