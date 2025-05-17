/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.model;

/**
 *
 * @author user
 */
import java.io.Serializable;
import java.util.Date;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int bookingId;
    private Customer customer;
    private Room room;
    private Date checkInDate;
    private Date checkOutDate;
    private Payment payment;
    private boolean status; // true = active, false = cancelled
    private boolean paid; // Added: track if booking is paid
    
    // Constructor
    public Booking(int bookingId, Customer customer, Room room, Date checkInDate, Date checkOutDate) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = true; // Active by default
        this.paid = false; // Not paid by default
    }
    
    // Method to calculate total bill
    public double calculateTotal() {
        // Calculate number of days
        long diffInMillies = checkOutDate.getTime() - checkInDate.getTime();
        int days = (int) (diffInMillies / (1000 * 60 * 60 * 24)) + 1; // +1 to include checkout day
        
        return room.getPrice() * days;
    }
    
    // Method to get total amount
    public double getTotalAmount() {
        return calculateTotal();
    }
    
    // Method to cancel a booking
    public boolean cancel() {
        if (status) {
            status = false;
            room.setStatus(true); // Make room available again
            return true;
        }
        return false;
    }
    
    // Getters and setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public boolean isPaid() {
        return paid;
    }
    
    public void setPaid(boolean paid) {
        this.paid = paid;
    }
    
    // Method to check if booking is cancelled
    public boolean isCancelled() {
        return !status;
    }
    
    // Method to set cancelled status
    public void setCancelled(boolean cancelled) {
        this.status = !cancelled;
    }
    
    @Override
    public String toString() {
        return "Booking [bookingId=" + bookingId + ", customer=" + customer.getName() + 
               ", room=" + room.getRoomNumber() + ", checkInDate=" + checkInDate + 
               ", checkOutDate=" + checkOutDate + ", status=" + (status ? "Active" : "Cancelled") + "]";
    }
}
