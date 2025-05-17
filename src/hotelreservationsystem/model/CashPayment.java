/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.model;

/**
 *
 * @author user
 */
import java.util.Date;

public class CashPayment extends Payment {
    private static final long serialVersionUID = 1L;
    
    private String receiptNumber;
    
    // Constructor
    public CashPayment(int paymentId, double amount, String receiptNumber) {
        super(paymentId, amount);
        this.receiptNumber = receiptNumber;
    }
    
    // Constructor with booking
    public CashPayment(Booking booking, double amount, Date paymentDate) {
        super(booking, amount, paymentDate);
        this.receiptNumber = "CASH-" + System.currentTimeMillis();
    }
    
    @Override
    public boolean process() {
        // Logic for cash payment processing
        System.out.println("Processing cash payment: $" + getAmount());
        return true;
    }
    
    @Override
    public String getPaymentMethod() {
        return "Cash";
    }
    
    // Getters and setters
    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", CashPayment [receiptNumber=" + receiptNumber + "]";
    }
}
