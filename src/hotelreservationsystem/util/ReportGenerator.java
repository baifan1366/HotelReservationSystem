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
     * @return Report object containing summary data
     */
    public static Report generateBookingSummaryReport() {
        Booking[] bookings = HotelReservationSystem.getBookings();
        int bookingCount = HotelReservationSystem.getBookingCount();
        
        Report report = new Report("Booking Summary Report", DateUtil.getTodayAsString());
        
        int activeBookingsCount = 0;
        int cancelledBookingsCount = 0;
        double totalRevenue = 0.0;
        
        for (int i = 0; i < bookingCount; i++) {
            Booking booking = bookings[i];
            if (booking != null) {
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
        
        report.addReportItem("Total Bookings", String.valueOf(activeBookingsCount + cancelledBookingsCount));
        report.addReportItem("Active Bookings", String.valueOf(activeBookingsCount));
        report.addReportItem("Cancelled Bookings", String.valueOf(cancelledBookingsCount));
        report.addReportItem("Total Revenue", "$" + String.format("%.2f", totalRevenue));
        
        return report;
    }
    
    /**
     * Generate a room occupancy report
     * @return Report object containing room occupancy data
     */
    public static Report generateRoomOccupancyReport() {
        Room[] rooms = HotelReservationSystem.getRooms();
        int roomCount = HotelReservationSystem.getRoomCount();
        
        Report report = new Report("Room Occupancy Report", DateUtil.getTodayAsString());
        
        int totalRooms = roomCount;
        int occupiedRooms = 0;
        int availableRooms = 0;
        
        for (int i = 0; i < roomCount; i++) {
            Room room = rooms[i];
            if (room != null) {
                if (room.isAvailable()) {
                    availableRooms++;
                } else {
                    occupiedRooms++;
                }
            }
        }
        
        double occupancyRate = (double) occupiedRooms / totalRooms * 100;
        
        report.addReportItem("Total Rooms", String.valueOf(totalRooms));
        report.addReportItem("Occupied Rooms", String.valueOf(occupiedRooms));
        report.addReportItem("Available Rooms", String.valueOf(availableRooms));
        report.addReportItem("Occupancy Rate", String.format("%.2f%%", occupancyRate));
        
        return report;
    }
    
    /**
     * Generate a payment method report
     * @return Report object containing payment method data
     */
    public static Report generatePaymentMethodReport() {
        Payment[] payments = HotelReservationSystem.getPayments();
        int paymentCount = HotelReservationSystem.getPaymentCount();
        
        Report report = new Report("Payment Method Report", DateUtil.getTodayAsString());
        
        int cashPaymentCount = 0;
        int cardPaymentCount = 0;
        double totalCashAmount = 0.0;
        double totalCardAmount = 0.0;
        
        for (int i = 0; i < paymentCount; i++) {
            Payment payment = payments[i];
            if (payment != null) {
                if (payment.getPaymentMethod().equals("Cash")) {
                    cashPaymentCount++;
                    totalCashAmount += payment.getAmount();
                } else if (payment.getPaymentMethod().equals("Card")) {
                    cardPaymentCount++;
                    totalCardAmount += payment.getAmount();
                }
            }
        }
        
        report.addReportItem("Total Payments", String.valueOf(paymentCount));
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
                if (checkInDate.compareTo(startDate) >= 0 && checkInDate.compareTo(endDate) <= 0) {
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
