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

public class AdminDashboard extends JFrame implements ActionListener {
    private Admin admin;
    private JTabbedPane tabbedPane;
    private JButton addRoomButton;
    private JButton addAdminButton;
    private JButton addAdminListButton;
    private JButton addCustomerListButton;
    private JButton generateReportButton;
    private JButton logoutButton;
    private JTable roomsTable;
    private DefaultTableModel roomsTableModel;
    private JTable bookingsTable;
    private DefaultTableModel bookingsTableModel;
    
    private RoomDAO roomDAO;
    private BookingDAO bookingDAO;
    
    ImageIcon icons[] = {
        new ImageIcon(getClass().getResource("/image/log-out.png")),
        new ImageIcon(getClass().getResource("/image/circle-check.png")),
        new ImageIcon(getClass().getResource("/image/circle-x.png")),
        new ImageIcon(getClass().getResource("/image/file-plus-2.png")),
        new ImageIcon(getClass().getResource("/image/user-round-plus.png")),
        new ImageIcon(getClass().getResource("/image/user-cog.png")),
        new ImageIcon(getClass().getResource("/image/users.png")),
        new ImageIcon(getClass().getResource("/image/clipboard-list.png"))
    };
    
    public AdminDashboard(Admin admin) {
        this.admin = admin;
        this.roomDAO = new RoomDAO();
        this.bookingDAO = new BookingDAO();
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
        JPanel buttonPanel = new JPanel(new GridLayout(1, 6, 10, 0));
        
        // Add room button
        addRoomButton = new JButton("New Room");
        addRoomButton.setIcon(icons[3]);
        addRoomButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        addRoomButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        addRoomButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        addRoomButton.setIconTextGap(10);
        addRoomButton.addActionListener(this);
        StyleConfig.applyStyle(addRoomButton);
        
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
                    booking.getCustomer().getFullName(),
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
}
