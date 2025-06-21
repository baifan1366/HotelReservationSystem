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
import hotelreservationsystem.dao.CommentDAO;
import hotelreservationsystem.model.Admin;
import hotelreservationsystem.model.Booking;
import hotelreservationsystem.model.Room;
import hotelreservationsystem.model.User;
import hotelreservationsystem.model.Comment;
import hotelreservationsystem.util.UUIDUtil;
import hotelreservationsystem.util.ValidationUtil;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminDashboard extends JFrame implements ActionListener {
    private Admin admin;
    private JTabbedPane tabbedPane;
    private JButton addRoomButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton addAdminButton;
    private JButton addAdminListButton;
    private JButton addCustomerListButton;
    private JButton generateReportButton;
    private JButton logoutButton;
    private JTable roomsTable;
    private DefaultTableModel roomsTableModel;
    private JTable bookingsTable;
    private DefaultTableModel bookingsTableModel;
    private JTable commentsTable;
    private DefaultTableModel commentsTableModel;
    
    private RoomDAO roomDAO;
    private BookingDAO bookingDAO;
    private CommentDAO commentDAO;
    
    ImageIcon icons[] = {
        new ImageIcon(getClass().getResource("/image/log-out.png")),
        new ImageIcon(getClass().getResource("/image/circle-check.png")),
        new ImageIcon(getClass().getResource("/image/circle-x.png")),
        new ImageIcon(getClass().getResource("/image/file-plus-2.png")),
        new ImageIcon(getClass().getResource("/image/user-round-plus.png")),
        new ImageIcon(getClass().getResource("/image/user-cog.png")),
        new ImageIcon(getClass().getResource("/image/users.png")),
        new ImageIcon(getClass().getResource("/image/clipboard-list.png")),
        new ImageIcon(getClass().getResource("/image/settings.png")),  // Edit icon
        new ImageIcon(getClass().getResource("/image/file-x-2.png"))   // Delete icon
    };
    
    public AdminDashboard(Admin admin) {
        this.admin = admin;
        this.roomDAO = new RoomDAO();
        this.bookingDAO = new BookingDAO();
        this.commentDAO = new CommentDAO();
        initComponents();
    }
    
    private void initComponents() {
        // Set frame properties
        setTitle("Admin Dashboard - " + admin.getFullName());
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
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        
        // Add room button
        addRoomButton = new JButton("New Room");
        addRoomButton.setIcon(icons[3]);
        addRoomButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        addRoomButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        addRoomButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        addRoomButton.setIconTextGap(10);
        addRoomButton.addActionListener(this);
        StyleConfig.applyStyle(addRoomButton);
        
        // Edit button (generic - will be context-sensitive)
        editButton = new JButton("Edit Room");
        editButton.setIcon(icons[8]);
        editButton.setHorizontalTextPosition(SwingConstants.LEFT);
        editButton.setVerticalTextPosition(SwingConstants.CENTER);
        editButton.setHorizontalAlignment(SwingConstants.CENTER);
        editButton.setIconTextGap(10);
        editButton.addActionListener(this);
        StyleConfig.applyStyle(editButton);
        editButton.setEnabled(false); // Initially disabled until an item is selected
        
        // Delete button (generic - will be context-sensitive)
        deleteButton = new JButton("Delete Room");
        deleteButton.setIcon(icons[9]);
        deleteButton.setHorizontalTextPosition(SwingConstants.LEFT);
        deleteButton.setVerticalTextPosition(SwingConstants.CENTER);
        deleteButton.setHorizontalAlignment(SwingConstants.CENTER);
        deleteButton.setIconTextGap(10);
        deleteButton.addActionListener(this);
        StyleConfig.applyStyle(deleteButton);
        deleteButton.setEnabled(false); // Initially disabled until an item is selected
        
        // Add admin button
        addAdminButton = new JButton("New Admin");
        addAdminButton.setIcon(icons[4]);
        addAdminButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        addAdminButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        addAdminButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        addAdminButton.setIconTextGap(10);
        addAdminButton.addActionListener(this);
        StyleConfig.applyStyle(addAdminButton);
        
        // Add admin list button
        addAdminListButton = new JButton("Admin List");
        addAdminListButton.setIcon(icons[5]);
        addAdminListButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        addAdminListButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        addAdminListButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        addAdminListButton.setIconTextGap(10);
        addAdminListButton.addActionListener(this);
        StyleConfig.applyStyle(addAdminListButton);
        
        // Add customer list button
        addCustomerListButton = new JButton("Customer List");
        addCustomerListButton.setIcon(icons[6]);
        addCustomerListButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        addCustomerListButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        addCustomerListButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        addCustomerListButton.setIconTextGap(10);
        addCustomerListButton.addActionListener(this);
        StyleConfig.applyStyle(addCustomerListButton);
        
        // Generate report button
        generateReportButton = new JButton("Report");
        generateReportButton.setIcon(icons[7]);
        generateReportButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        generateReportButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        generateReportButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        generateReportButton.setIconTextGap(10);
        generateReportButton.addActionListener(this);
        StyleConfig.applyStyle(generateReportButton);
        
        // Logout button
        logoutButton = new JButton("Logout");
        logoutButton.setIcon(icons[0]);
        logoutButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        logoutButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        logoutButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        logoutButton.setIconTextGap(10);
        logoutButton.addActionListener(this);
        StyleConfig.applyAccentStyle(logoutButton);
        
        buttonPanel.add(addRoomButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(addAdminButton);
        buttonPanel.add(addAdminListButton);
        buttonPanel.add(addCustomerListButton);
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
        
        // Add comments panel
        JPanel commentsPanel = createCommentsPanel();
        tabbedPane.addTab("Comments", commentsPanel);
        
        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Apply styling
        StyleConfig.applyStyle(mainPanel);
        StyleConfig.applyStyle(headerPanel);
        StyleConfig.applyStyle(buttonPanel);
        
        // Add tab change listener to update button labels and behavior
        tabbedPane.addChangeListener(e -> {
            updateButtonsForSelectedTab();
            // Extra check to ensure buttons are disabled when Comments tab is selected
            if (tabbedPane.getSelectedIndex() == 2) {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    // Method to update button labels and behavior based on the selected tab
    private void updateButtonsForSelectedTab() {
        int selectedTab = tabbedPane.getSelectedIndex();
        
        switch (selectedTab) {
            case 0: // Rooms tab
                editButton.setText("Edit Room");
                deleteButton.setText("Delete Room");
                editButton.setEnabled(roomsTable.getSelectedRow() != -1);
                deleteButton.setEnabled(roomsTable.getSelectedRow() != -1);
                break;
            case 1: // Bookings tab
                editButton.setText("Edit Booking");
                deleteButton.setText("Delete Booking");
                editButton.setEnabled(bookingsTable.getSelectedRow() != -1);
                deleteButton.setEnabled(bookingsTable.getSelectedRow() != -1);
                break;
            case 2: // Comments tab
                editButton.setText("Edit Comment");
                deleteButton.setText("Delete Comment");
                // Disable edit and delete buttons for comments
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
                break;
        }
    }
    
    private JPanel createRoomsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        // Create rooms table
        String[] columnNames = {"Room Number", "Room Type", "Price per Night", "Available", "Average Rating"};
        roomsTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };
        
        roomsTable = new JTable(roomsTableModel);
        roomsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(roomsTable);
        
        // Add mouse listener for room selection
        roomsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabbedPane.getSelectedIndex() == 0) {
                boolean hasSelection = roomsTable.getSelectedRow() != -1;
                editButton.setEnabled(hasSelection);
                deleteButton.setEnabled(hasSelection);
            }
        });
        
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
        bookingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        
        // Add selection listener for bookings
        bookingsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabbedPane.getSelectedIndex() == 1) {
                boolean hasSelection = bookingsTable.getSelectedRow() != -1;
                editButton.setEnabled(hasSelection);
                deleteButton.setEnabled(hasSelection);
            }
        });
        
        // Load bookings data
        loadBookingsData();
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createCommentsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        // Create comments table
        String[] columnNames = {"Comment ID", "Room Number", "Customer", "Rating", "Comment", "Date"};
        commentsTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };
        
        commentsTable = new JTable(commentsTableModel);
        JScrollPane scrollPane = new JScrollPane(commentsTable);
        
        // Add selection listener for comments - keep buttons disabled even when a comment is selected
        commentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        commentsTable.getSelectionModel().addListSelectionListener(e -> {
            // Always keep buttons disabled for comments, regardless of selection
            if (tabbedPane.getSelectedIndex() == 2) {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });
        
        // Load comments data
        loadCommentsData();
        
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
                // Calculate average rating for this room
                String avgRating = calculateAverageRating(room);
                
                Object[] rowData = {
                    room.getRoomNumber(),
                    room.getType(),
                    room.getPricePerNight(),
                    room.isAvailable() ? "Yes" : "No",
                    avgRating
                };
                roomsTableModel.addRow(rowData);
            }
        }
    }
    
    // Helper method to calculate average rating for a room
    private String calculateAverageRating(Room room) {
        Comment[] comments = commentDAO.getCommentsByRoom(room.getRoomId());
        if (comments.length == 0) {
            return "No ratings";
        }
        
        double totalRating = 0;
        int ratingCount = 0;
        
        for (Comment comment : comments) {
            if (comment != null) {
                totalRating += comment.getRating();
                ratingCount++;
            }
        }
        
        if (ratingCount == 0) {
            return "No ratings";
        }
        
        double avgRating = totalRating / ratingCount;
        return String.format("%.1f/5", avgRating);
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
                    booking.getCustomer().getFullName(),
                    booking.getRoom().getRoomNumber(),
                    booking.getCheckInDateString(),
                    booking.getCheckOutDateString(),
                    booking.isPaid() ? "Paid" : "Pending Payment"
                };
                bookingsTableModel.addRow(rowData);
            }
        }
    }
    
    private void loadCommentsData() {
        // Clear existing data
        commentsTableModel.setRowCount(0);
        
        // Get all rooms to fetch their comments
        Room[] rooms = HotelReservationSystem.getRooms();
        int roomCount = HotelReservationSystem.getRoomCount();
        
        // For each room, get its comments and add to table
        for (int i = 0; i < roomCount; i++) {
            Room room = rooms[i];
            if (room != null) {
                Comment[] roomComments = commentDAO.getCommentsByRoom(room.getRoomId());
                
                for (Comment comment : roomComments) {
                    if (comment != null) {
                        Object[] rowData = {
                            comment.getCommentId(),
                            room.getRoomNumber(),
                            comment.getCustomerName(),
                            comment.getRating() + "/5",
                            comment.getComment(),
                            comment.getCommentDate()
                        };
                        commentsTableModel.addRow(rowData);
                    }
                }
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addRoomButton) {
            addNewRoom();
        } else if (e.getSource() == editButton) {
            int selectedTab = tabbedPane.getSelectedIndex();
            switch (selectedTab) {
                case 0:
                    editSelectedRoom();
                    break;
                case 1:
                    editSelectedBooking();
                    break;
                case 2:
                    // No editing for comments - do nothing
                    break;
            }
        } else if (e.getSource() == deleteButton) {
            int selectedTab = tabbedPane.getSelectedIndex();
            switch (selectedTab) {
                case 0:
                    deleteSelectedRoom();
                    break;
                case 1:
                    deleteSelectedBooking();
                    break;
                case 2:
                    // No deletion for comments - do nothing
                    break;
            }
        } else if (e.getSource() == addAdminButton) {
            addNewAdmin();
        } else if (e.getSource() == addAdminListButton) {
            showAdminList();
        } else if (e.getSource() == addCustomerListButton) {
            showCustomerList();
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
            
            // radio button to select room type
            JPanel typePanel = new JPanel();
            JRadioButton singleBtn = new JRadioButton("Single");
            JRadioButton doubleBtn = new JRadioButton("Double");
            JRadioButton suiteBtn = new JRadioButton("Suite");
            ButtonGroup group = new ButtonGroup();
            group.add(singleBtn);
            group.add(doubleBtn);
            group.add(suiteBtn);
            singleBtn.setSelected(true);
            typePanel.add(singleBtn);
            typePanel.add(doubleBtn);
            typePanel.add(suiteBtn);
            int result = JOptionPane.showConfirmDialog(this, typePanel, "Select Room Type", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result != JOptionPane.OK_OPTION) return;
            String roomType = singleBtn.isSelected() ? "Single" : (doubleBtn.isSelected() ? "Double" : "Suite");
            
            double price = 0.0;
            while (true) {
                String priceStr = JOptionPane.showInputDialog(this, "Enter Price Per Night:");
                if (priceStr == null || priceStr.isEmpty()) return;
                try {
                    price = Double.parseDouble(priceStr);
                    if (price < 1.00) {
                        JOptionPane.showMessageDialog(this, "Price must be at least 1.00", "Input Error", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    // Format to 2 decimal places
                    price = Double.parseDouble(String.format("%.2f", price));
                    break;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid price format! Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
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
    
    private void editSelectedRoom() {
        int selectedRow = roomsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a room to edit.");
            return;
        }
        
        try {
            // Get selected room details
            int roomNumber = (int) roomsTableModel.getValueAt(selectedRow, 0);
            Room room = roomDAO.findRoomByNumber(roomNumber);
            
            if (room == null) {
                JOptionPane.showMessageDialog(this, "Room not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check if room has active bookings
            if (!room.isAvailable()) {
                JOptionPane.showMessageDialog(this, 
                    "Cannot edit room #" + roomNumber + " because it is currently booked.", 
                    "Room in Use", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Create edit room dialog
            JDialog dialog = new JDialog(this, "Edit Room", true);
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(this);
            
            // Create layout
            JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Form panel
            JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
            
            // Room number display (not editable)
            JLabel roomNumberLabel = new JLabel("Room Number:");
            JLabel roomNumberValue = new JLabel(String.valueOf(room.getRoomNumber()));
            formPanel.add(roomNumberLabel);
            formPanel.add(roomNumberValue);
            
            // Room type selection
            JLabel typeLabel = new JLabel("Room Type:");
            JPanel typePanel = new JPanel(new GridLayout(3, 1));
            JRadioButton singleBtn = new JRadioButton("Single");
            JRadioButton doubleBtn = new JRadioButton("Double");
            JRadioButton suiteBtn = new JRadioButton("Suite");
            
            // Set current room type
            if ("Single".equals(room.getType())) {
                singleBtn.setSelected(true);
            } else if ("Double".equals(room.getType())) {
                doubleBtn.setSelected(true);
            } else if ("Suite".equals(room.getType())) {
                suiteBtn.setSelected(true);
            }
            
            ButtonGroup group = new ButtonGroup();
            group.add(singleBtn);
            group.add(doubleBtn);
            group.add(suiteBtn);
            
            typePanel.add(singleBtn);
            typePanel.add(doubleBtn);
            typePanel.add(suiteBtn);
            
            formPanel.add(typeLabel);
            formPanel.add(typePanel);
            
            // Price field
            JLabel priceLabel = new JLabel("Price per Night:");
            JTextField priceField = new JTextField(String.valueOf(room.getPricePerNight()));
            formPanel.add(priceLabel);
            formPanel.add(priceField);
            
            // Buttons panel
            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton saveButton = new JButton("Save Changes");
            saveButton.setIcon(icons[1]);
            saveButton.setHorizontalTextPosition(SwingConstants.LEFT);
            
            JButton cancelButton = new JButton("Cancel");
            cancelButton.setIcon(icons[2]);
            cancelButton.setHorizontalTextPosition(SwingConstants.LEFT);
            
            buttonsPanel.add(saveButton);
            buttonsPanel.add(cancelButton);
            
            // Add panels to main panel
            mainPanel.add(formPanel, BorderLayout.CENTER);
            mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
            
            // Add action listeners
            saveButton.addActionListener(e -> {
                try {
                    // Get new values
                    String type = singleBtn.isSelected() ? "Single" : (doubleBtn.isSelected() ? "Double" : "Suite");
                    double price = Double.parseDouble(priceField.getText().trim());
                    
                    // Validate price
                    if (price < 1.00) {
                        JOptionPane.showMessageDialog(dialog, "Price must be at least 1.00", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // Format price to 2 decimal places
                    price = Double.parseDouble(String.format("%.2f", price));
                    
                    // Update room
                    room.setType(type);
                    room.setPrice(price);
                    boolean success = roomDAO.updateRoom(room);
                    
                    if (success) {
                        JOptionPane.showMessageDialog(dialog, "Room updated successfully!");
                        loadRoomsData(); // Refresh rooms table
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Failed to update room!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Invalid price format! Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            cancelButton.addActionListener(e -> dialog.dispose());
            
            // Apply styling
            StyleConfig.applyStyle(formPanel);
            StyleConfig.applyStyle(buttonsPanel);
            StyleConfig.applyStyle(saveButton);
            StyleConfig.applyAccentStyle(cancelButton);
            
            // Set content pane and show dialog
            dialog.setContentPane(mainPanel);
            dialog.setVisible(true);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error editing room: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteSelectedRoom() {
        int selectedRow = roomsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a room to delete.");
            return;
        }
        
        int roomNumber = (int) roomsTableModel.getValueAt(selectedRow, 0);
        Room room = roomDAO.findRoomByNumber(roomNumber);
        
        if (room == null) {
            JOptionPane.showMessageDialog(this, "Room not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if room is already booked/not available
        if (!room.isAvailable()) {
            JOptionPane.showMessageDialog(this, 
                "Cannot delete room #" + roomNumber + " because it is currently booked.", 
                "Room in Use", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete Room #" + roomNumber + "?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = roomDAO.deleteRoom(roomNumber);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Room #" + roomNumber + " deleted successfully.");
                loadRoomsData(); // Refresh rooms table
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to delete room. Please try again.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void addNewAdmin() {
        // Create dialog
        JDialog dialog = new JDialog(this, "Add New Admin", true);
        dialog.setSize(400, 450);
        dialog.setLocationRelativeTo(this);

        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Add New Admin");
        titleLabel.setIcon(icons[4]);
        titleLabel.setHorizontalTextPosition(SwingConstants.CENTER); 
        titleLabel.setVerticalTextPosition(SwingConstants.BOTTOM);  
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        StyleConfig.applyTitleStyle(titleLabel);
        titlePanel.add(titleLabel);
        StyleConfig.applyStyle(titlePanel);
        titlePanel.add(titleLabel);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField emailField = new JTextField();
        JTextField roleField = new JTextField();

        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Role:"));
        formPanel.add(roleField);

        // Bottom panel with buttons
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton saveButton = new JButton("Create");
        saveButton.setIcon(icons[1]);
        saveButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        saveButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        saveButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        saveButton.setIconTextGap(10);
        saveButton.setHorizontalTextPosition(SwingConstants.LEFT);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setIcon(icons[2]);
        cancelButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        cancelButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        cancelButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        cancelButton.setIconTextGap(10);

        bottomPanel.add(saveButton);
        bottomPanel.add(cancelButton);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add action listeners
        cancelButton.addActionListener(e -> dialog.dispose());

        saveButton.addActionListener(e -> {
            try {
                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String email = emailField.getText().trim();
                String role = roleField.getText().trim();
                String fullName = firstName + " " + lastName;
                String userId = "admin-" + UUIDUtil.generateShortUUID();
                String adminId = "ADM-" + UUIDUtil.generateShortUUID();

                if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() ||
                    email.isEmpty() || role.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "All fields are required!",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!ValidationUtil.isValidPassword(password)) {
                    JOptionPane.showMessageDialog(dialog,
                            "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one digit, and one special character.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!ValidationUtil.isValidEmail(email)) {
                    JOptionPane.showMessageDialog(dialog, "Invalid email format!",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                AdminDAO adminDAO = new AdminDAO();
                User[] users = HotelReservationSystem.getUsers();
                int userCount = HotelReservationSystem.getUserCount();
                for (int i = 0; i < userCount; i++) {
                    User user = users[i];
                    if (user instanceof Admin && user.getUsername() != null &&
                            user.getUsername().equals(username)) {
                        JOptionPane.showMessageDialog(dialog, "Username already exists!",
                                "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (adminDAO.findAdminByEmail(email) != null) {
                    JOptionPane.showMessageDialog(dialog, "Email already exists!",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Admin newAdmin = new Admin(fullName, username, password, email, adminId, role);

                if (userCount < users.length) {
                    users[userCount] = newAdmin;
                    HotelReservationSystem.setUserCount(userCount + 1);
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

        // Style application
        StyleConfig.applyStyle(formPanel);
        StyleConfig.applyStyle(bottomPanel);
        StyleConfig.applyStyle(saveButton);
        StyleConfig.applyAccentStyle(cancelButton);

        dialog.setContentPane(mainPanel);
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
        loadCommentsData();
    }
    
    // Placeholder method for showing admin list
    private void showAdminList() {
        JPanel adminPanel = loadAdminsData();
        JDialog dialog = new JDialog(this, "Admin List", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.add(adminPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    // Method to load admin data and return a panel with a table
    private JPanel loadAdminsData() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        String[] columnNames = {"User ID", "Full Name", "Username", "Email", "Password", "Admin ID", "Role"};
        DefaultTableModel adminTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable adminTable = new JTable(adminTableModel);
        JScrollPane scrollPane = new JScrollPane(adminTable);

        User[] users = HotelReservationSystem.getUsers();
        int userCount = HotelReservationSystem.getUserCount();
        for (int i = 0; i < userCount; i++) {
            User user = users[i];
            if (user instanceof Admin) {
                Admin admin = (Admin) user;
                Object[] rowData = {
                    admin.getUserId(),      // User ID
                    admin.getFullName(),    // Full Name
                    admin.getUsername(),    // Username
                    admin.getEmail(),
                    admin.getPassword(),
                    admin.getAdminId(),
                    admin.getRole()
                };
                adminTableModel.addRow(rowData);
            }
        }
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // Method for showing customer list
    private void showCustomerList() {
        JPanel customerPanel = loadCustomersData();
        JDialog dialog = new JDialog(this, "Customer List", true);
        dialog.setSize(700, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.add(customerPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    // Method to load customer data and return a panel with a table
    private JPanel loadCustomersData() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        String[] columnNames = {"User ID", "Full Name", "Username", "Email", "Password", "Phone", "Address"};
        DefaultTableModel customerTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable customerTable = new JTable(customerTableModel);
        JScrollPane scrollPane = new JScrollPane(customerTable);

        User[] users = HotelReservationSystem.getUsers();
        int userCount = HotelReservationSystem.getUserCount();
        for (int i = 0; i < userCount; i++) {
            User user = users[i];
            if (user instanceof hotelreservationsystem.model.Customer) {
                hotelreservationsystem.model.Customer customer = (hotelreservationsystem.model.Customer) user;
                Object[] rowData = {
                    customer.getUserId(),      // User ID
                    customer.getFullName(),    // Full Name
                    customer.getUsername(),    // Username
                    customer.getEmail(),
                    customer.getPassword(),
                    customer.getPhone(),
                    customer.getAddress()
                };
                customerTableModel.addRow(rowData);
            }
        }
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // Methods for editing and deleting bookings
    private void editSelectedBooking() {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to edit.");
            return;
        }
        
        try {
            // Get selected booking details
            String bookingId = String.valueOf(bookingsTableModel.getValueAt(selectedRow, 0));
            Booking booking = bookingDAO.getBookingById(bookingId);
            
            if (booking == null) {
                JOptionPane.showMessageDialog(this, "Booking not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create edit booking dialog
            JDialog dialog = new JDialog(this, "Edit Booking", true);
            dialog.setSize(450, 400); // Increased height for payment status
            dialog.setLocationRelativeTo(this);
            
            // Create layout
            JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Form panel - increased to 6 rows for payment status
            JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
            
            // Booking ID (non-editable)
            JLabel bookingIdLabel = new JLabel("Booking ID:");
            JLabel bookingIdValue = new JLabel(String.valueOf(booking.getBookingId()));
            formPanel.add(bookingIdLabel);
            formPanel.add(bookingIdValue);
            
            // Customer info (non-editable)
            JLabel customerLabel = new JLabel("Customer:");
            JLabel customerValue = new JLabel(booking.getCustomer().getFullName());
            formPanel.add(customerLabel);
            formPanel.add(customerValue);
            
            // Room number (non-editable) - Get directly from the table model for the selected row
            JLabel roomLabel = new JLabel("Room Number:");
            int roomNumber = (int) bookingsTableModel.getValueAt(selectedRow, 2);
            JLabel roomValue = new JLabel(String.valueOf(roomNumber));
            formPanel.add(roomLabel);
            formPanel.add(roomValue);
            
            // Check-in date (non-editable)
            JLabel checkInLabel = new JLabel("Check-in Date:");
            JTextField checkInField = new JTextField(booking.getCheckInDateString());
            checkInField.setEditable(false);
            formPanel.add(checkInLabel);
            formPanel.add(checkInField);
            
            // Check-out date (non-editable)
            JLabel checkOutLabel = new JLabel("Check-out Date:");
            JTextField checkOutField = new JTextField(booking.getCheckOutDateString());
            checkOutField.setEditable(false);
            formPanel.add(checkOutLabel);
            formPanel.add(checkOutField);
            
            // Payment status (editable)
            JLabel paymentLabel = new JLabel("Payment Status:");
            JPanel paymentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            JRadioButton paidBtn = new JRadioButton("Paid");
            JRadioButton pendingBtn = new JRadioButton("Pending Payment");
            ButtonGroup paymentGroup = new ButtonGroup();
            paymentGroup.add(paidBtn);
            paymentGroup.add(pendingBtn);
            
            // Set current payment status
            if (booking.isPaid()) {
                paidBtn.setSelected(true);
            } else {
                pendingBtn.setSelected(true);
            }
            
            paymentPanel.add(paidBtn);
            paymentPanel.add(pendingBtn);
            formPanel.add(paymentLabel);
            formPanel.add(paymentPanel);
            
            // Buttons panel
            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton saveButton = new JButton("Save Changes");
            saveButton.setIcon(icons[1]);
            saveButton.setHorizontalTextPosition(SwingConstants.LEFT);
            
            JButton cancelButton = new JButton("Cancel");
            cancelButton.setIcon(icons[2]);
            cancelButton.setHorizontalTextPosition(SwingConstants.LEFT);
            
            buttonsPanel.add(saveButton);
            buttonsPanel.add(cancelButton);
            
            // Add panels to main panel
            mainPanel.add(formPanel, BorderLayout.CENTER);
            mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
            
            // Add action listeners
            saveButton.addActionListener(e -> {
                try {
                    // Get new values
                    boolean isPaid = paidBtn.isSelected();
                    
                    // Update booking - only update payment status
                    booking.setPaid(isPaid);
                    boolean success = bookingDAO.updateBooking(booking);
                    
                    if (success) {
                        JOptionPane.showMessageDialog(dialog, "Booking updated successfully!");
                        loadBookingsData(); // Refresh bookings table
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Failed to update booking!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error updating booking: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            cancelButton.addActionListener(e -> dialog.dispose());
            
            // Apply styling
            StyleConfig.applyStyle(formPanel);
            StyleConfig.applyStyle(buttonsPanel);
            StyleConfig.applyStyle(saveButton);
            StyleConfig.applyAccentStyle(cancelButton);
            
            // Set content pane and show dialog
            dialog.setContentPane(mainPanel);
            dialog.setVisible(true);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error editing booking: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteSelectedBooking() {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to delete.");
            return;
        }
        
        String bookingId = String.valueOf(bookingsTableModel.getValueAt(selectedRow, 0));
        Booking booking = bookingDAO.getBookingById(bookingId);
        
        if (booking == null) {
            JOptionPane.showMessageDialog(this, "Booking not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel this booking?\nBooking ID: " + bookingId +
                "\nCustomer: " + booking.getCustomer().getFullName() +
                "\nRoom: " + booking.getRoom().getRoomNumber(),
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = bookingDAO.cancelBooking(bookingId);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully.");
                loadBookingsData(); // Refresh bookings table
                loadRoomsData();    // Refresh rooms table as availability might have changed
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to cancel booking. Please try again.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
