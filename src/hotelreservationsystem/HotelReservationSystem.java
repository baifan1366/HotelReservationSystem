/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hotelreservationsystem;

import hotelreservationsystem.ui.LoginForm;
import hotelreservationsystem.dao.FileManager;
import hotelreservationsystem.model.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author user
 */
public class HotelReservationSystem {
    // System capacity
    private static final int MAX_ROOMS = 20;
    private static final int MAX_USERS = 50;
    private static final int MAX_BOOKINGS = 100;
    private static final int MAX_PAYMENTS = 100;
    
    // Arrays to store system data
    private static Room[] rooms = new Room[MAX_ROOMS];
    private static User[] users = new User[MAX_USERS];
    private static Booking[] bookings = new Booking[MAX_BOOKINGS];
    private static Payment[] payments = new Payment[MAX_PAYMENTS];
    
    // Counters for array management
    private static int roomCount = 0;
    private static int userCount = 0;
    private static int bookingCount = 0;
    private static int paymentCount = 0;
    
    // FileManager to handle data persistence
    private static FileManager fileManager = new FileManager();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Initialize system with data from files
        loadData();
        
        // If no rooms exist, create some default rooms
        if (roomCount == 0) {
            initializeDefaultRooms();
        }
        
        // If no admin exists, create a default admin
        createDefaultAdminIfNeeded();
        
        // Launch the login form
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginForm loginForm = new LoginForm();
                loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                loginForm.setVisible(true);
            }
        });
    }
    
    private static void loadData() {
        // Load data from binary files
        Room[] loadedRooms = fileManager.loadRooms();
        if (loadedRooms != null) {
            rooms = loadedRooms;
            roomCount = countNonNullElements(rooms);
        }
        
        User[] loadedUsers = fileManager.loadUsers();
        if (loadedUsers != null) {
            users = loadedUsers;
            userCount = countNonNullElements(users);
        }
        
        Booking[] loadedBookings = fileManager.loadBookings();
        if (loadedBookings != null) {
            bookings = loadedBookings;
            bookingCount = countNonNullElements(bookings);
        }
        
        Payment[] loadedPayments = fileManager.loadPayments();
        if (loadedPayments != null) {
            payments = loadedPayments;
            paymentCount = countNonNullElements(payments);
        }
    }
    
    private static int countNonNullElements(Object[] array) {
        int count = 0;
        for (Object obj : array) {
            if (obj != null) {
                count++;
            }
        }
        return count;
    }
    
    private static void initializeDefaultRooms() {
        // Create default rooms with different types and prices
        rooms[0] = new Room(1, 101, "Single", 100.0, true);
        rooms[1] = new Room(2, 102, "Single", 100.0, true);
        rooms[2] = new Room(3, 201, "Double", 150.0, true);
        rooms[3] = new Room(4, 202, "Double", 150.0, true);
        rooms[4] = new Room(5, 301, "Suite", 250.0, true);
        roomCount = 5;
        
        // Save rooms to file
        fileManager.saveRooms(rooms);
    }
    
    private static void createDefaultAdminIfNeeded() {
        boolean adminExists = false;
        for (int i = 0; i < userCount; i++) {
            if (users[i] instanceof Admin) {
                adminExists = true;
                break;
            }
        }
        
        if (!adminExists) {
            Admin admin = new Admin("admin", "Admin", "admin123", "admin@hotel.com", "ADM001", "System Administrator");
            users[userCount++] = admin;
            fileManager.saveUsers(users);
        }
    }
    
    // Getters for data access
    public static Room[] getRooms() {
        return rooms;
    }
    
    public static User[] getUsers() {
        return users;
    }
    
    public static Booking[] getBookings() {
        return bookings;
    }
    
    public static Payment[] getPayments() {
        return payments;
    }
    
    // Methods to add new objects to arrays
    public static void addRoom(Room room) {
        if (roomCount < MAX_ROOMS) {
            rooms[roomCount++] = room;
            fileManager.saveRooms(rooms);
        }
    }
    
    public static void addUser(User user) {
        if (userCount < MAX_USERS) {
            users[userCount++] = user;
            fileManager.saveUsers(users);
        }
    }
    
    public static void addBooking(Booking booking) {
        if (bookingCount < MAX_BOOKINGS) {
            bookings[bookingCount++] = booking;
            fileManager.saveBookings(bookings);
        }
    }
    
    public static void addPayment(Payment payment) {
        if (paymentCount < MAX_PAYMENTS) {
            payments[paymentCount++] = payment;
            fileManager.savePayments(payments);
        }
    }
    
    // Room count getter
    public static int getRoomCount() {
        return roomCount;
    }
    
    // User count getter
    public static int getUserCount() {
        return userCount;
    }
    
    // Booking count getter
    public static int getBookingCount() {
        return bookingCount;
    }
    
    // Payment count getter
    public static int getPaymentCount() {
        return paymentCount;
    }
}
