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
        // Add booking to the system
        HotelReservationSystem.addBooking(booking);
        return true;
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
    
    // Cancel booking
    public boolean cancelBooking(int bookingId) {
        Booking[] bookings = HotelReservationSystem.getBookings();
        int bookingCount = HotelReservationSystem.getBookingCount();
        
        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getBookingId() == bookingId && !bookings[i].isCancelled()) {
                // Mark booking as cancelled
                bookings[i].setCancelled(true);
                
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
}
