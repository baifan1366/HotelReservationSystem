/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.ui;

import hotelreservationsystem.dao.BookingDAO;
import hotelreservationsystem.dao.RoomDAO;
import hotelreservationsystem.model.Booking;
import hotelreservationsystem.model.Customer;
import hotelreservationsystem.model.Room;
import hotelreservationsystem.util.DateUtil;
import hotelreservationsystem.util.ValidationUtil;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BookingForm extends JFrame implements ActionListener {
    private Customer customer;
    private CustomerDashboard dashboard;
    
    private JComboBox<String> roomTypeComboBox;
    private JComboBox<Integer> roomNumberComboBox;
    private JTextField checkInDateField;
    private JTextField checkOutDateField;
    private JLabel totalAmountLabel;
    private JButton bookButton;
    private JButton cancelButton;
    
    private RoomDAO roomDAO;
    private BookingDAO bookingDAO;
    
    private static int bookingIdCounter = 1000;
    
    public BookingForm(Customer customer, CustomerDashboard dashboard) {
        this.customer = customer;
        this.dashboard = dashboard;
        this.roomDAO = new RoomDAO();
        this.bookingDAO = new BookingDAO();
        initComponents();
    }
    
    private void initComponents() {
        // Set frame properties
        setTitle("Book a Room");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Book a Room");
        StyleConfig.applyTitleStyle(titleLabel);
        titlePanel.add(titleLabel);
        StyleConfig.applyStyle(titlePanel);
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        
        // Check-in date field
        JLabel checkInDateLabel = new JLabel("Check-in Date (3:00 PM):");
        checkInDateField = new JTextField(DateUtil.getTodayAsString());
        checkInDateField.addActionListener(this);
        StyleConfig.applyStyle(checkInDateLabel);
        StyleConfig.applyStyle(checkInDateField);
        formPanel.add(checkInDateLabel);
        formPanel.add(checkInDateField);
        
        // Check-out date field
        JLabel checkOutDateLabel = new JLabel("Check-out Date (12:00 PM):");
        checkOutDateField = new JTextField();
        checkOutDateField.addActionListener(this);
        StyleConfig.applyStyle(checkOutDateLabel);
        StyleConfig.applyStyle(checkOutDateField);
        formPanel.add(checkOutDateLabel);
        formPanel.add(checkOutDateField);
        
        // Room type combo box
        JLabel roomTypeLabel = new JLabel("Room Type:");
        roomTypeComboBox = new JComboBox<>(new String[]{"Single", "Double", "Suite"});
        roomTypeComboBox.addActionListener(this);
        StyleConfig.applyStyle(roomTypeLabel);
        formPanel.add(roomTypeLabel);
        formPanel.add(roomTypeComboBox);
        
        // Room number combo box
        JLabel roomNumberLabel = new JLabel("Room Number:");
        roomNumberComboBox = new JComboBox<>();
        roomNumberComboBox.addActionListener(this);
        StyleConfig.applyStyle(roomNumberLabel);
        formPanel.add(roomNumberLabel);
        formPanel.add(roomNumberComboBox);
        
        // Total amount label
        JLabel totalLabel = new JLabel("Total Amount:");
        totalAmountLabel = new JLabel("$0.00");
        StyleConfig.applyStyle(totalLabel);
        StyleConfig.applyHeadingStyle(totalAmountLabel);
        formPanel.add(totalLabel);
        formPanel.add(totalAmountLabel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        // Book button
        bookButton = new JButton("Book Now");
        bookButton.addActionListener(this);
        StyleConfig.applyStyle(bookButton);
        
        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        StyleConfig.applyAccentStyle(cancelButton);
        
        buttonPanel.add(bookButton);
        buttonPanel.add(cancelButton);
        
        // Add panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Apply styling
        StyleConfig.applyStyle(mainPanel);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private void updateRoomNumbers() {
        try {
            // Validate dates first
            Date checkInDate = DateUtil.parseCheckInDate(checkInDateField.getText());
            Date checkOutDate = DateUtil.parseCheckOutDate(checkOutDateField.getText());
            
            if (!ValidationUtil.isValidBookingDates(checkInDate, checkOutDate)) {
                JOptionPane.showMessageDialog(this, 
                        "Invalid dates. Check-in date must be today or in the future and check-out date must be after check-in date.", 
                        "Date Error", 
                        JOptionPane.ERROR_MESSAGE);
                roomNumberComboBox.removeAllItems();
                totalAmountLabel.setText("$0.00");
                return;
            }
            
            String selectedType = (String) roomTypeComboBox.getSelectedItem();
            Room[] rooms = roomDAO.getRoomsByType(selectedType);
            
            DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
            
            for (Room room : rooms) {
                if (room != null && isRoomAvailableForDates(room.getRoomNumber(), checkInDate, checkOutDate)) {
                    model.addElement(room.getRoomNumber());
                }
            }
            
            roomNumberComboBox.setModel(model);
            
            // Update total amount if we have room numbers
            if (model.getSize() > 0) {
                calculateTotalAmount();
            } else {
                totalAmountLabel.setText("No rooms available");
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, 
                    "Invalid date format. Please use yyyy-MM-dd", 
                    "Date Error", 
                    JOptionPane.ERROR_MESSAGE);
            roomNumberComboBox.removeAllItems();
            totalAmountLabel.setText("$0.00");
        }
    }
    
    private boolean isRoomAvailableForDates(int roomNumber, Date checkIn, Date checkOut) {
        // Get all bookings for this room
        Booking[] bookings = bookingDAO.getBookingsByRoomNumber(roomNumber);
        
        // Check for date conflicts
        for (Booking booking : bookings) {
            if (booking != null) {
                // Check if there's an overlap in dates
                Date bookedCheckIn = booking.getCheckInDate();
                Date bookedCheckOut = booking.getCheckOutDate();
                
                // If check-in date falls between an existing booking
                if ((checkIn.after(bookedCheckIn) || checkIn.equals(bookedCheckIn)) && 
                    checkIn.before(bookedCheckOut)) {
                    return false;
                }
                
                // If check-out date falls between an existing booking
                if (checkOut.after(bookedCheckIn) && 
                    (checkOut.before(bookedCheckOut) || checkOut.equals(bookedCheckOut))) {
                    return false;
                }
                
                // If booking period completely contains an existing booking
                if ((checkIn.before(bookedCheckIn) || checkIn.equals(bookedCheckIn)) && 
                    (checkOut.after(bookedCheckOut) || checkOut.equals(bookedCheckOut))) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private void calculateTotalAmount() {
        if (roomNumberComboBox.getItemCount() == 0) {
            totalAmountLabel.setText("No rooms available");
            return;
        }
        
        // Check if check-out date is empty
        if (checkOutDateField.getText().trim().isEmpty()) {
            totalAmountLabel.setText("Enter check-out date");
            return;
        }
        
        try {
            // Get selected room
            int roomNumber = (int) roomNumberComboBox.getSelectedItem();
            Room room = roomDAO.findRoomByNumber(roomNumber);
            
            // Get dates
            Date checkInDate = DateUtil.parseCheckInDate(checkInDateField.getText());
            Date checkOutDate = DateUtil.parseCheckOutDate(checkOutDateField.getText());
            
            // Validate dates
            if (!ValidationUtil.isValidBookingDates(checkInDate, checkOutDate)) {
                totalAmountLabel.setText("Invalid dates");
                return;
            }
            
            // Calculate days
            int days = DateUtil.getDaysBetween(checkInDate, checkOutDate);
            
            // Calculate total
            double total = room.getPricePerNight() * days;
            
            totalAmountLabel.setText(String.format("$%.2f", total));
            
        } catch (ParseException e) {
            totalAmountLabel.setText("Invalid date format");
        } catch (Exception e) {
            totalAmountLabel.setText("Error calculating total");
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkInDateField || e.getSource() == checkOutDateField) {
            if (!checkInDateField.getText().trim().isEmpty() && !checkOutDateField.getText().trim().isEmpty()) {
                // Reset room selections when dates change
                roomTypeComboBox.setSelectedIndex(0);
                roomNumberComboBox.removeAllItems();
                totalAmountLabel.setText("$0.00");
                updateRoomNumbers();
            }
        } else if (e.getSource() == roomTypeComboBox) {
            if (!checkInDateField.getText().trim().isEmpty() && !checkOutDateField.getText().trim().isEmpty()) {
                updateRoomNumbers();
            }
        } else if (e.getSource() == roomNumberComboBox) {
            calculateTotalAmount();
        } else if (e.getSource() == bookButton) {
            bookRoom();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
    
    private void bookRoom() {
        if (roomNumberComboBox.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, 
                    "No rooms available for selected type and dates", 
                    "Booking Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Get selected room
            int roomNumber = (int) roomNumberComboBox.getSelectedItem();
            Room room = roomDAO.findRoomByNumber(roomNumber);
            
            // Get dates
            Date checkInDate = DateUtil.parseCheckInDate(checkInDateField.getText());
            Date checkOutDate = DateUtil.parseCheckOutDate(checkOutDateField.getText());
            
            // Validate dates
            if (!ValidationUtil.isValidBookingDates(checkInDate, checkOutDate)) {
                JOptionPane.showMessageDialog(this, 
                        "Invalid dates. Check-in date must be today or in the future and check-out date must be after check-in date.", 
                        "Booking Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Double check availability
            if (!isRoomAvailableForDates(roomNumber, checkInDate, checkOutDate)) {
                JOptionPane.showMessageDialog(this, 
                        "Sorry, this room is no longer available for the selected dates.", 
                        "Booking Error", 
                        JOptionPane.ERROR_MESSAGE);
                updateRoomNumbers();
                return;
            }
            
            // Calculate days
            int days = DateUtil.getDaysBetween(checkInDate, checkOutDate);
            
            // Calculate total
            double total = room.getPricePerNight() * days;
            
            // Create booking
            Booking booking = new Booking(
                    bookingIdCounter++, 
                    customer, 
                    room, 
                    checkInDate, 
                    checkOutDate);
            
            // Save booking
            boolean success = bookingDAO.createBooking(booking);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                        "Room booked successfully! Proceed to payment.", 
                        "Booking Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                
                // Open payment form
                PaymentForm paymentForm = new PaymentForm(booking, dashboard);
                paymentForm.setVisible(true);
                
                // Close booking form
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                        "Error creating booking", 
                        "Booking Error", 
                        JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, 
                    "Invalid date format. Please use yyyy-MM-dd", 
                    "Booking Error", 
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "Error: " + e.getMessage(), 
                    "Booking Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
