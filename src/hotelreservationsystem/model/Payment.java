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

public abstract class Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int paymentId;
    private double amount;
    private Date paymentDate;
    private Booking booking;
    
    // Constructor
    public Payment(int paymentId, double amount) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = new Date();  // Current date
    }
    
    // Constructor with booking
    public Payment(Booking booking, double amount, Date paymentDate) {
        this.paymentId = generatePaymentId();
        this.booking = booking;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }
    
    // Method to generate a payment ID
    private int generatePaymentId() {
        return (int) (Math.random() * 100000);
    }
    
    // Method to get payment method (to be implemented by subclasses)
    public String getPaymentMethod() {
        return this.getClass().getSimpleName();
    }
    
    // Abstract method to process payment
    public abstract boolean process();
    
    // Getters and setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public Booking getBooking() {
        return booking;
    }
    
    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    
    @Override
    public String toString() {
        return "Payment [paymentId=" + paymentId + ", amount=" + amount + 
               ", paymentDate=" + paymentDate + "]";
    }
}
