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
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

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
    
    private JPanel roomInfoPanel;

    private JLabel bed, airVent, baggageClaim, bath;
    private JLabel bedDouble, alarmClock, brushCleaning, cigaretteOff, utensils;
    private JLabel airplay, carTaxiFront, conciergeBell, plane;
    
    ImageIcon icons[] = {
        new ImageIcon(getClass().getResource("/image/circle-check.png")),
        new ImageIcon(getClass().getResource("/image/circle-x.png")),
        new ImageIcon(getClass().getResource("/image/air-vent.png")),
        new ImageIcon(getClass().getResource("/image/baggage-claim.png")),
        new ImageIcon(getClass().getResource("/image/bed-double.png")),
        new ImageIcon(getClass().getResource("/image/car-taxi-front.png")),
        new ImageIcon(getClass().getResource("/image/airplay.png")),
        new ImageIcon(getClass().getResource("/image/alarm-clock.png")),
        new ImageIcon(getClass().getResource("/image/bath.png")),
        new ImageIcon(getClass().getResource("/image/brush-cleaning.png")),
        new ImageIcon(getClass().getResource("/image/cigarette-off.png")),
        new ImageIcon(getClass().getResource("/image/concierge-bell.png")),
        new ImageIcon(getClass().getResource("/image/plane.png")),
        new ImageIcon(getClass().getResource("/image/utensils.png")),
        new ImageIcon(getClass().getResource("/image/bed.png")),
        new ImageIcon(getClass().getResource("/image/file-plus-2.png"))
    };
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
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Book a Room");
        titleLabel.setIcon(icons[15]);
        titleLabel.setHorizontalTextPosition(SwingConstants.CENTER); 
        titleLabel.setVerticalTextPosition(SwingConstants.BOTTOM);  
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        StyleConfig.applyTitleStyle(titleLabel);
        titlePanel.add(titleLabel);
        StyleConfig.applyStyle(titlePanel);
        
        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
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
        
        // Room info panel
        roomInfoPanel = new JPanel(new GridLayout(3, 4)); 
        
        airVent = new JLabel("Air Conditional");
        airVent.setIcon(icons[2]);
        airVent.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        airVent.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        airVent.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        airVent.setIconTextGap(10);
        
        baggageClaim = new JLabel("Luggage Storage");
        baggageClaim.setIcon(icons[3]);
        baggageClaim.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        baggageClaim.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        baggageClaim.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        baggageClaim.setIconTextGap(10);
        
        bedDouble = new JLabel("Double Bed");
        bedDouble.setIcon(icons[4]);
        bedDouble.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        bedDouble.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        bedDouble.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        bedDouble.setIconTextGap(10);
        
        carTaxiFront = new JLabel("Car Taxi");
        carTaxiFront.setIcon(icons[5]);
        carTaxiFront.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        carTaxiFront.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        carTaxiFront.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        carTaxiFront.setIconTextGap(10);
        
        airplay = new JLabel("Television");
        airplay.setIcon(icons[6]);
        airplay.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        airplay.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        airplay.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        airplay.setIconTextGap(10);
        
        alarmClock = new JLabel("Wake-up Call");
        alarmClock.setIcon(icons[7]);
        alarmClock.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        alarmClock.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        alarmClock.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        alarmClock.setIconTextGap(10);
        
        bath = new JLabel("Bathroom");
        bath.setIcon(icons[8]);
        bath.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        bath.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        bath.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        bath.setIconTextGap(10);
        
        brushCleaning = new JLabel("Housekeeping");
        brushCleaning.setIcon(icons[9]);
        brushCleaning.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        brushCleaning.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        brushCleaning.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        brushCleaning.setIconTextGap(10);
        
        cigaretteOff = new JLabel("Non-smoking");
        cigaretteOff.setIcon(icons[10]);
        cigaretteOff.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        cigaretteOff.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        cigaretteOff.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        cigaretteOff.setIconTextGap(10);
        
        conciergeBell = new JLabel("Room Service");
        conciergeBell.setIcon(icons[11]);
        conciergeBell.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        conciergeBell.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        conciergeBell.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        conciergeBell.setIconTextGap(10);
        
        plane = new JLabel("Near the Airport");
        plane.setIcon(icons[12]);
        plane.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        plane.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        plane.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        plane.setIconTextGap(10);
        
        utensils = new JLabel("Meals");
        utensils.setIcon(icons[13]);
        utensils.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        utensils.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        utensils.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        utensils.setIconTextGap(10);
        
        bed = new JLabel("Single Bed");
        bed.setIcon(icons[14]);
        bed.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        bed.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        bed.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        bed.setIconTextGap(10);
        
        // Trigger initial display for "Single"
        updateRoomInfo("Single");
        
        // get roomTypeLabel, then display different labelIcon
        roomTypeComboBox.addActionListener(e -> {
            String selected = (String) roomTypeComboBox.getSelectedItem();
            updateRoomInfo(selected);
        });

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        // Book button
        bookButton = new JButton("Book Now");
        bookButton.setIcon(icons[0]);
        bookButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        bookButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        bookButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        bookButton.setIconTextGap(10);
        bookButton.addActionListener(this);
        StyleConfig.applyStyle(bookButton);
        
        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.setIcon(icons[1]);
        cancelButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        cancelButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        cancelButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        cancelButton.setIconTextGap(10);
        cancelButton.addActionListener(this);
        StyleConfig.applyAccentStyle(cancelButton);
        
        buttonPanel.add(bookButton);
        buttonPanel.add(cancelButton);
        
        // Add panels to main panel
        centerPanel.add(formPanel);
        centerPanel.add(roomInfoPanel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Apply styling
        StyleConfig.applyStyle(mainPanel);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private void updateRoomInfo(String selected) {
        roomInfoPanel.removeAll();
        switch (selected) {
            case "Single":
                roomInfoPanel.add(bed);
                roomInfoPanel.add(airVent);
                roomInfoPanel.add(baggageClaim);
                roomInfoPanel.add(bath);
                break;
            case "Double":
                roomInfoPanel.add(bedDouble);
                roomInfoPanel.add(airVent);
                roomInfoPanel.add(alarmClock);
                roomInfoPanel.add(baggageClaim);
                roomInfoPanel.add(bath);
                roomInfoPanel.add(brushCleaning);
                roomInfoPanel.add(cigaretteOff);
                roomInfoPanel.add(utensils);
                break;
            case "Suite":
                roomInfoPanel.add(bed);
                roomInfoPanel.add(bedDouble);
                roomInfoPanel.add(airVent);
                roomInfoPanel.add(airplay);
                roomInfoPanel.add(alarmClock);
                roomInfoPanel.add(baggageClaim);
                roomInfoPanel.add(bath);
                roomInfoPanel.add(brushCleaning);
                roomInfoPanel.add(carTaxiFront);
                roomInfoPanel.add(cigaretteOff);
                roomInfoPanel.add(conciergeBell);
                roomInfoPanel.add(plane);
                break;
        }
        roomInfoPanel.revalidate();
        roomInfoPanel.repaint();
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
