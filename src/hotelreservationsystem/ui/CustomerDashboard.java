/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.ui;

import hotelreservationsystem.dao.BookingDAO;
import hotelreservationsystem.model.Booking;
import hotelreservationsystem.model.Customer;
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
import javax.swing.table.DefaultTableModel;

public class CustomerDashboard extends JFrame implements ActionListener {
    private Customer customer;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JButton bookRoomButton;
    private JButton viewBookingsButton;
    private JButton cancelBookingButton;
    private JButton logoutButton;
    private JButton viewRoomsButton;
    private JButton commentButton;
    private JTable bookingsTable;
    private DefaultTableModel bookingsTableModel;
    
    private BookingDAO bookingDAO;
    
    public CustomerDashboard(Customer customer) {
        this.customer = customer;
        this.bookingDAO = new BookingDAO();
        initComponents();
    }
    
    private void initComponents() {
        // Set frame properties
        setTitle("Customer Dashboard - " + customer.getName());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome, " + customer.getName() + "!");
        StyleConfig.applyTitleStyle(welcomeLabel);
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        
        // Create navigation panel
        JPanel navPanel = new JPanel(new GridLayout(1, 6, 10, 0));
        
        // Book room button
        bookRoomButton = new JButton("Book a Room");
        bookRoomButton.addActionListener(this);
        StyleConfig.applyStyle(bookRoomButton);
        
        // View bookings button
        viewBookingsButton = new JButton("My Bookings");
        viewBookingsButton.addActionListener(this);
        StyleConfig.applyStyle(viewBookingsButton);
        
        // Cancel booking button
        cancelBookingButton = new JButton("Cancel Booking");
        cancelBookingButton.addActionListener(this);
        StyleConfig.applyStyle(cancelBookingButton);
        
        // View all rooms button
        viewRoomsButton = new JButton("All Rooms");
        viewRoomsButton.addActionListener(this);
        StyleConfig.applyStyle(viewRoomsButton);
        
        // Comment and rate button
        commentButton = new JButton("Add Comments");
        commentButton.addActionListener(this);
        StyleConfig.applyStyle(commentButton);
        
        // Logout button
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        StyleConfig.applyAccentStyle(logoutButton);
        
        navPanel.add(bookRoomButton);
        navPanel.add(viewBookingsButton);
        navPanel.add(cancelBookingButton);
        navPanel.add(viewRoomsButton);
        navPanel.add(commentButton);
        navPanel.add(logoutButton);
        
        // Create card panel for different views
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        // Create welcome panel (default view)
        JPanel welcomePanel = new JPanel(new BorderLayout());
        JLabel infoLabel = new JLabel("Use the buttons above to navigate", JLabel.CENTER);
        StyleConfig.applyStyle(infoLabel);
        welcomePanel.add(infoLabel, BorderLayout.CENTER);
        
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
        } else if (e.getSource() == commentButton) {
            openRoomDetailsDialog(); // Open room details dialog on comments tab
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
    
    // Getter for customer object (needed for the comment form)
    public Customer getCustomer() {
        return customer;
    }
}
