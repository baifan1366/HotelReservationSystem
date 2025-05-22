/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.util;

import hotelreservationsystem.HotelReservationSystem;
import hotelreservationsystem.model.Booking;
import hotelreservationsystem.model.Payment;
import hotelreservationsystem.model.Report;
import hotelreservationsystem.model.Room;
import java.util.Date;

/**
 *
 * @author user
 */
public class ReportGenerator {
    
    /**
     * Generate a summary report for all bookings
     * @param startDate Start date for the report
     * @param endDate End date for the report
     * @return Report object containing summary data
     */
    public static Report generateBookingSummaryReport(Date startDate, Date endDate) {
        Booking[] bookings = HotelReservationSystem.getBookings();
        int bookingCount = HotelReservationSystem.getBookingCount();
        
        Report report = new Report("Booking Summary Report", 
                DateUtil.formatDate(startDate) + " to " + DateUtil.formatDate(endDate));
        
        int activeBookingsCount = 0;
        int cancelledBookingsCount = 0;
        double totalRevenue = 0.0;
        
        for (int i = 0; i < bookingCount; i++) {
            Booking booking = bookings[i];
            if (booking != null) {
                Date bookingDate = booking.getCheckInDate();
                // Filter by date range if booking date is available
                if (bookingDate != null && 
                    bookingDate.compareTo(startDate) >= 0 && 
                    bookingDate.compareTo(endDate) <= 0) {
                    if (booking.isCancelled()) {
                        cancelledBookingsCount++;
                    } else {
                        activeBookingsCount++;
                        if (booking.isPaid()) {
                            totalRevenue += booking.getTotalAmount();
                        }
                    }
                }
            }
        }
        
        report.addReportItem("Date Range", DateUtil.formatDate(startDate) + " to " + DateUtil.formatDate(endDate));
        report.addReportItem("Total Bookings", String.valueOf(activeBookingsCount + cancelledBookingsCount));
        report.addReportItem("Active Bookings", String.valueOf(activeBookingsCount));
        report.addReportItem("Cancelled Bookings", String.valueOf(cancelledBookingsCount));
        report.addReportItem("Total Revenue", "$" + String.format("%.2f", totalRevenue));
        
        return report;
    }
    
    /**
     * Generate a room occupancy report
     * @param startDate Start date for the report
     * @param endDate End date for the report
     * @return Report object containing room occupancy data
     */
    public static Report generateRoomOccupancyReport(Date startDate, Date endDate) {
        Room[] rooms = HotelReservationSystem.getRooms();
        int roomCount = HotelReservationSystem.getRoomCount();
        Booking[] bookings = HotelReservationSystem.getBookings();
        int bookingCount = HotelReservationSystem.getBookingCount();
        
        Report report = new Report("Room Occupancy Report", 
                DateUtil.formatDate(startDate) + " to " + DateUtil.formatDate(endDate));
        
        int totalRooms = roomCount;
        int occupiedRooms = 0;
        int availableRooms = totalRooms;
        
        // For occupancy report, consider a room occupied if there's any booking for it
        // within the date range
        for (int i = 0; i < bookingCount; i++) {
            Booking booking = bookings[i];
            if (booking != null && !booking.isCancelled()) {
                Date checkInDate = booking.getCheckInDate();
                Date checkOutDate = booking.getCheckOutDate();
                
                // Check if booking dates overlap with the report date range
                if (checkInDate != null && checkOutDate != null && 
                    checkInDate.compareTo(endDate) <= 0 && 
                    checkOutDate.compareTo(startDate) >= 0) {
                    // This booking is active in the date range
                    occupiedRooms++;
                    availableRooms--;
                    // Avoid counting the same room twice
                    break;
                }
            }
        }
        
        double occupancyRate = totalRooms > 0 ? (double) occupiedRooms / totalRooms * 100 : 0;
        
        report.addReportItem("Date Range", DateUtil.formatDate(startDate) + " to " + DateUtil.formatDate(endDate));
        report.addReportItem("Total Rooms", String.valueOf(totalRooms));
        report.addReportItem("Occupied Rooms", String.valueOf(occupiedRooms));
        report.addReportItem("Available Rooms", String.valueOf(availableRooms));
        report.addReportItem("Occupancy Rate", String.format("%.2f%%", occupancyRate));
        
        return report;
    }
    
    /**
     * Generate a payment method report
     * @param startDate Start date for the report
     * @param endDate End date for the report
     * @return Report object containing payment method data
     */
    public static Report generatePaymentMethodReport(Date startDate, Date endDate) {
        Payment[] payments = HotelReservationSystem.getPayments();
        int paymentCount = HotelReservationSystem.getPaymentCount();
        
        Report report = new Report("Payment Method Report", 
                DateUtil.formatDate(startDate) + " to " + DateUtil.formatDate(endDate));
        
        int cashPaymentCount = 0;
        int cardPaymentCount = 0;
        double totalCashAmount = 0.0;
        double totalCardAmount = 0.0;
        
        for (int i = 0; i < paymentCount; i++) {
            Payment payment = payments[i];
            if (payment != null) {
                Date paymentDate = payment.getPaymentDate();
                // Filter by date range
                if (paymentDate != null && 
                    paymentDate.compareTo(startDate) >= 0 && 
                    paymentDate.compareTo(endDate) <= 0) {
                    if (payment.getPaymentMethod().equals("Cash")) {
                        cashPaymentCount++;
                        totalCashAmount += payment.getAmount();
                    } else if (payment.getPaymentMethod().equals("Card")) {
                        cardPaymentCount++;
                        totalCardAmount += payment.getAmount();
                    }
                }
            }
        }
        
        report.addReportItem("Date Range", DateUtil.formatDate(startDate) + " to " + DateUtil.formatDate(endDate));
        report.addReportItem("Total Payments", String.valueOf(cashPaymentCount + cardPaymentCount));
        report.addReportItem("Cash Payments", String.valueOf(cashPaymentCount));
        report.addReportItem("Card Payments", String.valueOf(cardPaymentCount));
        report.addReportItem("Total Cash Amount", "$" + String.format("%.2f", totalCashAmount));
        report.addReportItem("Total Card Amount", "$" + String.format("%.2f", totalCardAmount));
        
        return report;
    }
    
    /**
     * Generate a revenue report for a specific date range
     * @param startDate Start date
     * @param endDate End date
     * @return Report object containing revenue data
     */
    public static Report generateRevenueReport(Date startDate, Date endDate) {
        Booking[] bookings = HotelReservationSystem.getBookings();
        int bookingCount = HotelReservationSystem.getBookingCount();
        
        Report report = new Report("Revenue Report", 
                DateUtil.formatDate(startDate) + " to " + DateUtil.formatDate(endDate));
        
        double totalRevenue = 0.0;
        int bookingsInRange = 0;
        
        for (int i = 0; i < bookingCount; i++) {
            Booking booking = bookings[i];
            if (booking != null && booking.isPaid() && !booking.isCancelled()) {
                Date checkInDate = booking.getCheckInDate();
                if (checkInDate != null && checkInDate.compareTo(startDate) >= 0 && checkInDate.compareTo(endDate) <= 0) {
                    totalRevenue += booking.getTotalAmount();
                    bookingsInRange++;
                }
            }
        }
        
        report.addReportItem("Date Range", DateUtil.formatDate(startDate) + " to " + DateUtil.formatDate(endDate));
        report.addReportItem("Bookings in Range", String.valueOf(bookingsInRange));
        report.addReportItem("Total Revenue", "$" + String.format("%.2f", totalRevenue));
        
        return report;
    }
}
