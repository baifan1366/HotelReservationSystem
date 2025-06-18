/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.util;

import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author user
 */
public class ValidationUtil {
    
    /**
     * Validate username
     * @param username Username to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidUsername(String username) {
        // Username should be at least 3 characters and contain only letters, numbers, and underscores
        return username != null && username.matches("^[a-zA-Z0-9_]{3,20}$");
    }
    
    /**
     * Validate password
     * @param password Password to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        // Password should be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one digit, and one special character.
        if (password == null || password.length() < 8) return false;
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if ("!@#$%^&*()-_=+[]{}|;:'\",.<>/?`~".indexOf(c) >= 0) hasSpecial = true;
        }
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
    
    /**
     * Validate name (first name or last name)
     * @param name Name to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidName(String name) {
        // Name should contain only letters and be at least 2 characters
        return name != null && name.matches("^[a-zA-Z ]{2,30}$");
    }
    
    /**
     * Validate room number
     * @param roomNumber Room number to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidRoomNumber(int roomNumber) {
        // Room number should be positive
        return roomNumber > 0;
    }
    
    /**
     * Validate room price
     * @param price Price to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPrice(double price) {
        // Price should be positive
        return price > 0;
    }
    
    /**
     * Validate booking dates
     * @param checkInDate Check-in date
     * @param checkOutDate Check-out date
     * @return true if valid, false otherwise
     */
    public static boolean isValidBookingDates(Date checkInDate, Date checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            return false;
        }
        
        // Check-in date should be in the future
        if (!DateUtil.isFutureDate(checkInDate)) {
            return false;
        }
        
        // Check-out date should be at least one day after check-in date
        return DateUtil.isValidDateRange(checkInDate, checkOutDate);
    }
    
    /**
     * Validate date string format
     * @param dateStr Date string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidDateFormat(String dateStr) {
        try {
            DateUtil.parseDate(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    /**
     * Validate booking dates format and logic
     * @param checkInDateStr Check-in date string in yyyy-MM-dd format
     * @param checkOutDateStr Check-out date string in yyyy-MM-dd format
     * @return true if valid, false otherwise
     */
    public static boolean isValidBookingDateStrings(String checkInDateStr, String checkOutDateStr) {
        try {
            Date checkInDate = DateUtil.parseCheckInDate(checkInDateStr);
            Date checkOutDate = DateUtil.parseCheckOutDate(checkOutDateStr);
            return isValidBookingDates(checkInDate, checkOutDate);
        } catch (ParseException e) {
            return false;
        }
    }
    
    /**
     * Validate credit card number
     * @param cardNumber Credit card number to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidCreditCardNumber(String cardNumber) {
        // Remove spaces and dashes
        cardNumber = cardNumber.replaceAll("[ -]", "");
        
        // Credit card number should contain only digits and be 13-19 digits long
        return cardNumber.matches("^\\d{13,19}$");
    }
    
    /**
     * Validate credit card expiry date
     * @param month Expiry month (1-12)
     * @param year Expiry year (4 digits)
     * @return true if valid, false otherwise
     */
    public static boolean isValidExpiryDate(int month, int year) {
        if (month < 1 || month > 12) {
            return false;
        }
        
        // Get current year and month
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int currentYear = calendar.get(java.util.Calendar.YEAR);
        int currentMonth = calendar.get(java.util.Calendar.MONTH) + 1;
        
        // Check if card is expired
        if (year < currentYear) {
            return false;
        } else if (year == currentYear && month < currentMonth) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Validate CVV number
     * @param cvv CVV number to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidCVV(String cvv) {
        // CVV should be 3 or 4 digits only, no letters or spaces allowed
        return cvv != null && cvv.trim().matches("^[0-9]{3}$");
    }
    
    /**
     * Validate email format
     * @param email Email to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        // Basic email regex for format validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }
}
