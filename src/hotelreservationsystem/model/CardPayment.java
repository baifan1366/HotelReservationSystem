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

public class CardPayment extends Payment {
    private static final long serialVersionUID = 1L;
    
    private String cardNumber;
    private String cardHolder;
    private int expiryMonth;
    private int expiryYear;
    private String cvv;
    private String cardType;  // Visa, MasterCard, etc.
    
    // Constructor
    public CardPayment(int paymentId, double amount, String cardNumber, String cardType, String expiryDate) {
        super(paymentId, amount);
        this.cardNumber = cardNumber;
        this.cardType = cardType;
    }
    
    // Constructor with booking and card details
    public CardPayment(Booking booking, double amount, Date paymentDate, 
                      String cardNumber, String cardHolder, 
                      int expiryMonth, int expiryYear, String cvv) {
        super(booking, amount, paymentDate);
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvv = cvv;
        this.cardType = determineCardType(cardNumber);
    }
    
    // Helper method to determine card type from card number
    private String determineCardType(String cardNumber) {
        if (cardNumber.startsWith("4")) {
            return "Visa";
        } else if (cardNumber.startsWith("5")) {
            return "MasterCard";
        } else if (cardNumber.startsWith("3")) {
            return "American Express";
        } else {
            return "Unknown";
        }
    }
    
    @Override
    public boolean process() {
        // Logic for card payment processing
        if (validate()) {
            System.out.println("Processing card payment: $" + getAmount());
            return true;
        }
        return false;
    }
    
    @Override
    public String getPaymentMethod() {
        return "Credit Card";
    }
    
    // Method to validate card details
    public boolean validate() {
        // Card validation logic
        return cardNumber != null && !cardNumber.isEmpty() && 
               cardHolder != null && !cardHolder.isEmpty() &&
               cvv != null && !cvv.isEmpty();
    }
    
    // Getters and setters
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    
    public String getCardHolder() {
        return cardHolder;
    }
    
    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }
    
    public int getExpiryMonth() {
        return expiryMonth;
    }
    
    public void setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
    }
    
    public int getExpiryYear() {
        return expiryYear;
    }
    
    public void setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
    }
    
    public String getCvv() {
        return cvv;
    }
    
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", CardPayment [cardNumber=" + maskCardNumber() + 
               ", cardType=" + cardType + "]";
    }
    
    // Helper method to mask card number for security
    private String maskCardNumber() {
        if (cardNumber != null && cardNumber.length() >= 4) {
            return "xxxx-xxxx-xxxx-" + cardNumber.substring(cardNumber.length() - 4);
        }
        return "Invalid Card";
    }
}
