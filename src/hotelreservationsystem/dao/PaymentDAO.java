/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.dao;

import hotelreservationsystem.HotelReservationSystem;
import hotelreservationsystem.model.Booking;
import hotelreservationsystem.model.Payment;

/**
 *
 * @author user
 */
public class PaymentDAO {
    
    // Create a new payment
    public boolean createPayment(Payment payment) {
        // Add payment to system
        HotelReservationSystem.addPayment(payment);
        
        // Update booking payment status based on payment status
        Booking booking = payment.getBooking();
        
        // Set the booking's paid status based on the payment status
        // Only mark as paid if status is "Paid", otherwise it's pending
        booking.setPaid("Paid".equals(payment.getStatus()));
        
        // Set payment reference in booking
        booking.setPayment(payment);
        
        // Save booking changes
        Booking[] bookings = HotelReservationSystem.getBookings();
        int bookingCount = HotelReservationSystem.getBookingCount();
        
        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getBookingId() == booking.getBookingId()) {
                bookings[i].setPaid("Paid".equals(payment.getStatus()));
                bookings[i].setPayment(payment);
                new FileManager().saveBookings(bookings);
                break;
            }
        }
        
        return true;
    }
    
    // Get payment by booking ID
    public Payment findPaymentByBookingId(int bookingId) {
        Payment[] payments = HotelReservationSystem.getPayments();
        int paymentCount = HotelReservationSystem.getPaymentCount();
        
        for (int i = 0; i < paymentCount; i++) {
            if (payments[i].getBooking().getBookingId() == bookingId) {
                return payments[i];
            }
        }
        return null;
    }
    
    // Get all payments
    public Payment[] getAllPayments() {
        return HotelReservationSystem.getPayments();
    }
    
    // Get payments by payment method
    public Payment[] getPaymentsByMethod(String paymentMethod) {
        Payment[] allPayments = HotelReservationSystem.getPayments();
        int paymentCount = HotelReservationSystem.getPaymentCount();
        
        // Count payments with specified method
        int methodCount = 0;
        for (int i = 0; i < paymentCount; i++) {
            if (allPayments[i].getPaymentMethod().equals(paymentMethod)) {
                methodCount++;
            }
        }
        
        // Create array of payments with specified method
        Payment[] methodPayments = new Payment[methodCount];
        int index = 0;
        for (int i = 0; i < paymentCount; i++) {
            if (allPayments[i].getPaymentMethod().equals(paymentMethod)) {
                methodPayments[index++] = allPayments[i];
            }
        }
        
        return methodPayments;
    }
}
