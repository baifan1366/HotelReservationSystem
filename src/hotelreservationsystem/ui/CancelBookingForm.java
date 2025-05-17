/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.ui;

import hotelreservationsystem.dao.BookingDAO;
import hotelreservationsystem.model.Booking;
import hotelreservationsystem.model.Customer;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CancelBookingForm extends JFrame implements ActionListener {
    private Customer customer;
    private CustomerDashboard dashboard;
    
    private JTable bookingsTable;
    private DefaultTableModel bookingsTableModel;
    private JButton cancelBookingButton;
    private JButton backButton;
    
    private BookingDAO bookingDAO;
    
    public CancelBookingForm(Customer customer, CustomerDashboard dashboard) {
        this.customer = customer;
        this.dashboard = dashboard;
        this.bookingDAO = new BookingDAO();
        initComponents();
    }
    
    private void initComponents() {
        // Set frame properties
        setTitle("Cancel Booking");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Cancel Booking");
        StyleConfig.applyTitleStyle(titleLabel);
        titlePanel.add(titleLabel);
        StyleConfig.applyStyle(titlePanel);
        
        // Create bookings table
        String[] columnNames = {"Booking ID", "Room Number", "Room Type", "Check-in Date", "Check-out Date", "Amount", "Status"};
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
        
        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        // Cancel booking button
        cancelBookingButton = new JButton("Cancel Selected Booking");
        cancelBookingButton.addActionListener(this);
        StyleConfig.applyStyle(cancelBookingButton);
        
        // Back button
        backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(this);
        StyleConfig.applyAccentStyle(backButton);
        
        buttonPanel.add(cancelBookingButton);
        buttonPanel.add(backButton);
        
        // Add panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Apply styling
        StyleConfig.applyStyle(mainPanel);
        StyleConfig.applyStyle(buttonPanel);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private void loadBookingsData() {
        // Clear existing data
        bookingsTableModel.setRowCount(0);
        
        // Get customer bookings
        Booking[] bookings = bookingDAO.getBookingsByCustomer(customer);
        
        // Add bookings to table
        for (Booking booking : bookings) {
            if (booking != null) {
                Object[] rowData = {
                    booking.getBookingId(),
                    booking.getRoom().getRoomNumber(),
                    booking.getRoom().getType(),
                    booking.getCheckInDate(),
                    booking.getCheckOutDate(),
                    String.format("$%.2f", booking.getTotalAmount()),
                    booking.isPaid() ? "Paid" : "Pending Payment"
                };
                bookingsTableModel.addRow(rowData);
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelBookingButton) {
            cancelSelectedBooking();
        } else if (e.getSource() == backButton) {
            dispose();
        }
    }
    
    private void cancelSelectedBooking() {
        int selectedRow = bookingsTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a booking to cancel",
                    "Cancel Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int bookingId = (int) bookingsTableModel.getValueAt(selectedRow, 0);
        
        // Confirm cancellation
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel booking #" + bookingId + "?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = bookingDAO.cancelBooking(bookingId);
            
            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Booking cancelled successfully!",
                        "Cancellation Success",
                        JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh booking list
                loadBookingsData();
                
                // Refresh dashboard
                dashboard.refreshDashboard();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error cancelling booking",
                        "Cancellation Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
