/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.dao;

import hotelreservationsystem.HotelReservationSystem;
import hotelreservationsystem.model.Booking;
import hotelreservationsystem.model.Customer;
import hotelreservationsystem.model.Room;

/**
 *
 * @author user
 */
public class BookingDAO {
    
    // Create a new booking
    public boolean createBooking(Booking booking) {
        // Set a unique ID for the booking
        int nextId = getNextBookingId();
        booking.setBookingId(nextId);
        
        // Add booking to the system
        HotelReservationSystem.addBooking(booking);
        return true;
    }
    
    // Get the next available booking ID
    public int getNextBookingId() {
        Booking[] bookings = HotelReservationSystem.getBookings();
        int bookingCount = HotelReservationSystem.getBookingCount();
        int maxId = 1000; // Start from 1000 if no bookings exist
        
        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i] != null && bookings[i].getBookingId() > maxId) {
                maxId = bookings[i].getBookingId();
            }
        }
        
        // Next ID is max + 1
        return maxId + 1;
    }
    
    // Get bookings for a specific customer
    public Booking[] getBookingsByCustomer(Customer customer) {
        Booking[] allBookings = HotelReservationSystem.getBookings();
        int bookingCount = HotelReservationSystem.getBookingCount();
        
        // Count bookings for this customer
        int customerBookingCount = 0;
        for (int i = 0; i < bookingCount; i++) {
            if (allBookings[i].getCustomer().getUserId().equals(customer.getUserId()) &&
                !allBookings[i].isCancelled()) {
                customerBookingCount++;
            }
        }
        
        // Create array of customer's bookings
        Booking[] customerBookings = new Booking[customerBookingCount];
        int index = 0;
        for (int i = 0; i < bookingCount; i++) {
            if (allBookings[i].getCustomer().getUserId().equals(customer.getUserId()) &&
                !allBookings[i].isCancelled()) {
                customerBookings[index++] = allBookings[i];
            }
        }
        
        return customerBookings;
    }
    
    // Find booking by ID
    public Booking findBookingById(int bookingId) {
        Booking[] bookings = HotelReservationSystem.getBookings();
        int bookingCount = HotelReservationSystem.getBookingCount();
        
        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getBookingId() == bookingId) {
                return bookings[i];
            }
        }
        return null;
    }
    
    // Find booking by ID and room number for more precision
    public Booking findBookingByIdAndRoom(int bookingId, int roomNumber) {
        Booking[] bookings = HotelReservationSystem.getBookings();
        int bookingCount = HotelReservationSystem.getBookingCount();
        
        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getBookingId() == bookingId && 
                bookings[i].getRoom().getRoomNumber() == roomNumber) {
                return bookings[i];
            }
        }
        return null;
    }
    
    // Cancel booking
    public boolean cancelBooking(int bookingId, int roomNumber) {
        Booking[] bookings = HotelReservationSystem.getBookings();
        int bookingCount = HotelReservationSystem.getBookingCount();
        
        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getBookingId() == bookingId && 
                bookings[i].getRoom().getRoomNumber() == roomNumber && 
                !bookings[i].isCancelled()) {
                
                // Mark booking as cancelled
                bookings[i].setCancelled(true);
                
                // Make room available
                bookings[i].getRoom().setStatus(true);
                
                // Save changes
                new FileManager().saveBookings(bookings);
                return true;
            }
        }
        return false;
    }
    
    // Overloaded method for backward compatibility
    public boolean cancelBooking(int bookingId) {
        Booking[] bookings = HotelReservationSystem.getBookings();
        int bookingCount = HotelReservationSystem.getBookingCount();
        
        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getBookingId() == bookingId && !bookings[i].isCancelled()) {
                // Mark booking as cancelled
                bookings[i].setCancelled(true);
                
                // Make room available
                bookings[i].getRoom().setStatus(true);
                
                // Save changes
                new FileManager().saveBookings(bookings);
                return true;
            }
        }
        return false;
    }
    
    // Get all active bookings
    public Booking[] getAllActiveBookings() {
        Booking[] allBookings = HotelReservationSystem.getBookings();
        int bookingCount = HotelReservationSystem.getBookingCount();
        
        // Count active bookings
        int activeCount = 0;
        for (int i = 0; i < bookingCount; i++) {
            if (!allBookings[i].isCancelled()) {
                activeCount++;
            }
        }
        
        // Create array of active bookings
        Booking[] activeBookings = new Booking[activeCount];
        int index = 0;
        for (int i = 0; i < bookingCount; i++) {
            if (!allBookings[i].isCancelled()) {
                activeBookings[index++] = allBookings[i];
            }
        }
        
        return activeBookings;
    }
    
    /**
     * Get bookings by room number
     * @param roomNumber Room number to search for
     * @return Array of bookings for the specified room
     */
    public Booking[] getBookingsByRoomNumber(int roomNumber) {
        Booking[] allBookings = HotelReservationSystem.getBookings();
        int bookingCount = HotelReservationSystem.getBookingCount();
        
        // Count bookings for this room
        int roomBookingCount = 0;
        for (int i = 0; i < bookingCount; i++) {
            if (allBookings[i].getRoom().getRoomNumber() == roomNumber && 
                !allBookings[i].isCancelled()) {
                roomBookingCount++;
            }
        }
        
        // Create array of room's bookings
        Booking[] roomBookings = new Booking[roomBookingCount];
        int index = 0;
        for (int i = 0; i < bookingCount; i++) {
            if (allBookings[i].getRoom().getRoomNumber() == roomNumber && 
                !allBookings[i].isCancelled()) {
                roomBookings[index++] = allBookings[i];
            }
        }
        
        return roomBookings;
    }
    
    /**
     * Get a booking by its ID (String version)
     * @param bookingId The booking ID as a string
     * @return The booking or null if not found
     */
    public Booking getBookingById(String bookingId) {
        try {
            int id = Integer.parseInt(bookingId);
            return findBookingById(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * Update an existing booking
     * @param booking The booking to update
     * @return true if successful, false otherwise
     */
    public boolean updateBooking(Booking booking) {
        Booking[] bookings = HotelReservationSystem.getBookings();
        int bookingCount = HotelReservationSystem.getBookingCount();
        
        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getBookingId() == booking.getBookingId() && 
                !bookings[i].isCancelled()) {
                
                // Update only payment status, not dates
                bookings[i].setPaid(booking.isPaid()); 
                
                // Save changes
                new FileManager().saveBookings(bookings);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Cancel a booking by its ID (String version)
     * @param bookingId The booking ID as a string
     * @return true if successful, false otherwise
     */
    public boolean cancelBooking(String bookingId) {
        try {
            int id = Integer.parseInt(bookingId);
            return cancelBooking(id);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
