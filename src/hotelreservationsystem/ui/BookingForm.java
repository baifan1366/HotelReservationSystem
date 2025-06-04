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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
        // Add document listener to check-in date field for real-time updates
        checkInDateField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Don't validate while typing, just update fields if format looks complete
                String text = checkInDateField.getText();
                if (text.length() == 10 && text.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    updateDateDependentFields();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Only clear data if field becomes empty
                if (checkInDateField.getText().isEmpty()) {
                    roomNumberComboBox.removeAllItems();
                    totalAmountLabel.setText("$0.00");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Rarely triggered for plain text fields
            }
        });
        
        // Add focus listener for validation on field exit
        checkInDateField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                validateDateField(checkInDateField);
            }
        });
        
        StyleConfig.applyStyle(checkInDateLabel);
        StyleConfig.applyStyle(checkInDateField);
        formPanel.add(checkInDateLabel);
        formPanel.add(checkInDateField);
        
        // Check-out date field
        JLabel checkOutDateLabel = new JLabel("Check-out Date (12:00 PM):");
        checkOutDateField = new JTextField();
        checkOutDateField.addActionListener(this);
        // Add document listener to check-out date field for real-time updates
        checkOutDateField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Don't validate while typing, just update fields if format looks complete
                String text = checkOutDateField.getText();
                if (text.length() == 10 && text.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    updateDateDependentFields();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Only clear data if field becomes empty
                if (checkOutDateField.getText().isEmpty()) {
                    roomNumberComboBox.removeAllItems();
                    totalAmountLabel.setText("$0.00");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Rarely triggered for plain text fields
            }
        });
        
        // Add focus listener for validation on field exit
        checkOutDateField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                validateDateField(checkOutDateField);
            }
        });
        
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
    
    // Method to validate a date field when focus is lost
    private void validateDateField(JTextField field) {
        if (!field.getText().trim().isEmpty()) {
            try {
                // Try to parse the date
                if (field == checkInDateField) {
                    Date date = DateUtil.parseCheckInDate(field.getText());
                    
                    // Check if it's a future date if needed
                    if (!DateUtil.isFutureDate(date)) {
                        JOptionPane.showMessageDialog(this,
                                "Check-in date must be today or in the future.",
                                "Date Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    Date checkOutDate = DateUtil.parseCheckOutDate(field.getText());
                    
                    // If check-in date is filled, validate date range
                    if (!checkInDateField.getText().trim().isEmpty()) {
                        try {
                            Date checkInDate = DateUtil.parseCheckInDate(checkInDateField.getText());
                            if (!DateUtil.isValidDateRange(checkInDate, checkOutDate)) {
                                JOptionPane.showMessageDialog(this,
                                        "Check-out date must be at least one day after check-in date.",
                                        "Date Error",
                                        JOptionPane.ERROR_MESSAGE);
                            } else {
                                // Valid dates, update rooms
                                updateRoomNumbers();
                            }
                        } catch (ParseException ex) {
                            // Check-in date has invalid format, handled separately
                        }
                    }
                }
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this,
                        "Invalid date format. Please use yyyy-MM-dd",
                        "Date Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Method to update all fields that depend on date changes
    private void updateDateDependentFields() {
        if (!checkInDateField.getText().trim().isEmpty() && !checkOutDateField.getText().trim().isEmpty()) {
            try {
                // Only validate the complete date format, not partial entries
                Date checkInDate = DateUtil.parseCheckInDate(checkInDateField.getText());
                Date checkOutDate = DateUtil.parseCheckOutDate(checkOutDateField.getText());
                
                // If dates are valid, update room numbers without showing error dialogs
                if (ValidationUtil.isValidBookingDates(checkInDate, checkOutDate)) {
                    updateRoomNumbersQuietly();
                }
            } catch (ParseException e) {
                // Ignore parse exceptions during typing - will be caught when focus changes
            }
        }
    }
    
    private void updateRoomNumbersQuietly() {
        try {
            // Validate dates first
            Date checkInDate = DateUtil.parseCheckInDate(checkInDateField.getText());
            Date checkOutDate = DateUtil.parseCheckOutDate(checkOutDateField.getText());
            
            if (!ValidationUtil.isValidBookingDates(checkInDate, checkOutDate)) {
                // Don't show error message, just clear results
                roomNumberComboBox.removeAllItems();
                totalAmountLabel.setText("$0.00");
                return;
            }
            
            String selectedType = (String) roomTypeComboBox.getSelectedItem();
            Room[] rooms = roomDAO.getRoomsByType(selectedType);
            
            System.out.println("Found " + (rooms != null ? rooms.length : 0) + " rooms of type " + selectedType);
            
            DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
            
            for (Room room : rooms) {
                if (room != null && isRoomAvailableForDates(room.getRoomNumber(), checkInDate, checkOutDate)) {
                    model.addElement(room.getRoomNumber());
                    System.out.println("Adding available room: " + room.getRoomNumber() + 
                                   ", Type: " + room.getType() + 
                                   ", Price: $" + room.getPricePerNight());
                }
            }
            
            roomNumberComboBox.setModel(model);
            
            // Make sure a room is selected if available
            if (model.getSize() > 0) {
                roomNumberComboBox.setSelectedIndex(0);
                System.out.println("Selected room: " + roomNumberComboBox.getSelectedItem());
                calculateTotalAmount();
            } else {
                totalAmountLabel.setText("No rooms available");
            }
        } catch (ParseException e) {
            // Don't show dialog, just clear the rooms
            roomNumberComboBox.removeAllItems();
            totalAmountLabel.setText("$0.00");
        } catch (Exception e) {
            System.out.println("Error in updateRoomNumbers: " + e.getMessage());
            e.printStackTrace();
            totalAmountLabel.setText("$0.00");
        }
    }
    
    private void updateRoomNumbers() {
        try {
            // Validate dates first
            Date checkInDate = DateUtil.parseCheckInDate(checkInDateField.getText());
            Date checkOutDate = DateUtil.parseCheckOutDate(checkOutDateField.getText());
            
            if (!ValidationUtil.isValidBookingDates(checkInDate, checkOutDate)) {
                JOptionPane.showMessageDialog(this, 
                        "Invalid dates. Check-in date must be today or in the future and check-out date must be at least one day after check-in date.", 
                        "Date Error", 
                        JOptionPane.ERROR_MESSAGE);
                roomNumberComboBox.removeAllItems();
                totalAmountLabel.setText("$0.00");
                return;
            }
            
            String selectedType = (String) roomTypeComboBox.getSelectedItem();
            Room[] rooms = roomDAO.getRoomsByType(selectedType);
            
            System.out.println("Found " + (rooms != null ? rooms.length : 0) + " rooms of type " + selectedType);
            
            DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
            
            for (Room room : rooms) {
                if (room != null && isRoomAvailableForDates(room.getRoomNumber(), checkInDate, checkOutDate)) {
                    model.addElement(room.getRoomNumber());
                    System.out.println("Adding available room: " + room.getRoomNumber() + 
                                   ", Type: " + room.getType() + 
                                   ", Price: $" + room.getPricePerNight());
                }
            }
            
            roomNumberComboBox.setModel(model);
            
            // Make sure a room is selected if available
            if (model.getSize() > 0) {
                roomNumberComboBox.setSelectedIndex(0);
                System.out.println("Selected room: " + roomNumberComboBox.getSelectedItem());
                calculateTotalAmount();
            } else {
                totalAmountLabel.setText("No rooms available");
            }
        } catch (ParseException e) {
            // Only show error message if both fields have content but format is wrong
            if (!checkInDateField.getText().trim().isEmpty() && !checkOutDateField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                        "Invalid date format. Please use yyyy-MM-dd", 
                        "Date Error", 
                        JOptionPane.ERROR_MESSAGE);
            }
            roomNumberComboBox.removeAllItems();
            totalAmountLabel.setText("$0.00");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error in updateRoomNumbers: " + e.getMessage());
            e.printStackTrace();
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
            // Check if room is selected
            if (roomNumberComboBox.getSelectedItem() == null) {
                // Select the first room by default if none is selected
                if (roomNumberComboBox.getItemCount() > 0) {
                    roomNumberComboBox.setSelectedIndex(0);
                } else {
                    totalAmountLabel.setText("No room selected");
                    return;
                }
            }
            
            // Get selected room
            int roomNumber = (int) roomNumberComboBox.getSelectedItem();
            Room room = roomDAO.findRoomByNumber(roomNumber);
            
            if (room == null) {
                totalAmountLabel.setText("Room not found");
                return;
            }
            
            // Get dates
            Date checkInDate = DateUtil.parseCheckInDate(checkInDateField.getText());
            Date checkOutDate = DateUtil.parseCheckOutDate(checkOutDateField.getText());
            
            // Validate dates
            if (!ValidationUtil.isValidBookingDates(checkInDate, checkOutDate)) {
                totalAmountLabel.setText("Invalid dates");
                return;
            }
            
            // Calculate days between check-in and check-out
            int days = DateUtil.getDaysBetween(checkInDate, checkOutDate);
            
            // Calculate total price
            double total = room.getPricePerNight() * days;
            
            // Debug
            System.out.println("Room: " + roomNumber + ", Type: " + room.getType() + 
                           ", Price per night: $" + room.getPricePerNight() + 
                           ", Days: " + days + ", Total: $" + total);
            
            totalAmountLabel.setText(String.format("$%.2f", total));
            
        } catch (ParseException e) {
            totalAmountLabel.setText("Invalid date format");
            e.printStackTrace();
        } catch (Exception e) {
            totalAmountLabel.setText("Error calculating total");
            e.printStackTrace();
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
                        "Invalid dates. Check-in date must be today or in the future and check-out date must be at least one day after check-in date.", 
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
