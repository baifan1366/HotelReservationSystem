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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

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
                    } else if (payment.getPaymentMethod().equals("Credit Card")) {
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
    
    /**
     * Generate a report showing popular room types based on bookings
     * @param startDate Start date for the report
     * @param endDate End date for the report
     * @return Report object containing popular room types data
     */
    public static Report generatePopularRoomTypesReport(Date startDate, Date endDate) {
        Booking[] bookings = HotelReservationSystem.getBookings();
        int bookingCount = HotelReservationSystem.getBookingCount();
        Room[] rooms = HotelReservationSystem.getRooms();
        int roomCount = HotelReservationSystem.getRoomCount();
        
        Report report = new Report("Popular Room Types Report", 
                DateUtil.formatDate(startDate) + " to " + DateUtil.formatDate(endDate));
        
        // Map to store count of bookings by room type
        Map<String, Integer> roomTypeBookings = new HashMap<>();
        // Map to store revenue by room type
        Map<String, Double> roomTypeRevenue = new HashMap<>();
        
        for (int i = 0; i < bookingCount; i++) {
            Booking booking = bookings[i];
            if (booking != null && !booking.isCancelled()) {
                Date bookingDate = booking.getCheckInDate();
                // Filter by date range
                if (bookingDate != null && 
                    bookingDate.compareTo(startDate) >= 0 && 
                    bookingDate.compareTo(endDate) <= 0) {
                    
                    int roomId = booking.getRoomId();
                    for (int j = 0; j < roomCount; j++) {
                        Room room = rooms[j];
                        if (room != null && room.getRoomId() == roomId) {
                            String roomType = room.getType();
                            
                            // Update room type booking count
                            roomTypeBookings.put(roomType, roomTypeBookings.getOrDefault(roomType, 0) + 1);
                            
                            // Update room type revenue
                            if (booking.isPaid()) {
                                roomTypeRevenue.put(roomType, roomTypeRevenue.getOrDefault(roomType, 0.0) + booking.getTotalAmount());
                            }
                            
                            break;
                        }
                    }
                }
            }
        }
        
        // Add report items
        report.addReportItem("Date Range", DateUtil.formatDate(startDate) + " to " + DateUtil.formatDate(endDate));
        report.addReportItem("Total Bookings Analyzed", String.valueOf(bookingCount));
        
        // Find the most popular room type
        String mostPopularRoomType = "";
        int maxBookings = 0;
        
        for (Map.Entry<String, Integer> entry : roomTypeBookings.entrySet()) {
            String roomType = entry.getKey();
            int count = entry.getValue();
            
            report.addReportItem(roomType + " Rooms Booked", String.valueOf(count));
            report.addReportItem(roomType + " Revenue", "$" + String.format("%.2f", roomTypeRevenue.getOrDefault(roomType, 0.0)));
            
            if (count > maxBookings) {
                maxBookings = count;
                mostPopularRoomType = roomType;
            }
        }
        
        // Add most popular room type if data exists
        if (!mostPopularRoomType.isEmpty()) {
            report.addReportItem("Most Popular Room Type", mostPopularRoomType + " (" + maxBookings + " bookings)");
        }
        
        return report;
    }
    
    /**
     * Generate a cancellation analysis report
     * @param startDate Start date for the report
     * @param endDate End date for the report
     * @return Report object containing cancellation analysis data
     */
    public static Report generateCancellationAnalysisReport(Date startDate, Date endDate) {
        Booking[] bookings = HotelReservationSystem.getBookings();
        int bookingCount = HotelReservationSystem.getBookingCount();
        
        Report report = new Report("Cancellation Analysis Report", 
                DateUtil.formatDate(startDate) + " to " + DateUtil.formatDate(endDate));
        
        int totalBookings = 0;
        int cancelledBookings = 0;
        double totalLostRevenue = 0.0;
        
        // Map to track cancellations by room type
        Map<String, Integer> cancellationsByRoomType = new HashMap<>();
        
        for (int i = 0; i < bookingCount; i++) {
            Booking booking = bookings[i];
            if (booking != null) {
                Date bookingDate = booking.getCheckInDate();
                // Filter by date range
                if (bookingDate != null && 
                    bookingDate.compareTo(startDate) >= 0 && 
                    bookingDate.compareTo(endDate) <= 0) {
                    
                    totalBookings++;
                    
                    if (booking.isCancelled()) {
                        cancelledBookings++;
                        totalLostRevenue += booking.getTotalAmount();
                        
                        // Get room ID and update cancellation by room type
                        int roomId = booking.getRoomId();
                        Room[] rooms = HotelReservationSystem.getRooms();
                        int roomCount = HotelReservationSystem.getRoomCount();
                        
                        for (int j = 0; j < roomCount; j++) {
                            Room room = rooms[j];
                            if (room != null && room.getRoomId() == roomId) {
                                String roomType = room.getType();
                                cancellationsByRoomType.put(roomType, cancellationsByRoomType.getOrDefault(roomType, 0) + 1);
                                break;
                            }
                        }
                    }
                }
            }
        }
        
        // Calculate cancellation rate
        double cancellationRate = totalBookings > 0 ? (double) cancelledBookings / totalBookings * 100 : 0;
        
        // Add report items
        report.addReportItem("Date Range", DateUtil.formatDate(startDate) + " to " + DateUtil.formatDate(endDate));
        report.addReportItem("Total Bookings", String.valueOf(totalBookings));
        report.addReportItem("Cancelled Bookings", String.valueOf(cancelledBookings));
        report.addReportItem("Cancellation Rate", String.format("%.2f%%", cancellationRate));
        report.addReportItem("Total Lost Revenue", "$" + String.format("%.2f", totalLostRevenue));
        
        // Add cancellations by room type
        for (Map.Entry<String, Integer> entry : cancellationsByRoomType.entrySet()) {
            report.addReportItem(entry.getKey() + " Room Cancellations", String.valueOf(entry.getValue()));
        }
        
        return report;
    }
    
    /**
     * Generate a report on guest feedback and comments
     * @param startDate Start date for the report
     * @param endDate End date for the report
     * @return Report object containing guest feedback data
     */
    public static Report generateGuestFeedbackReport(Date startDate, Date endDate) {
        // Use CommentDAO to access comments
        hotelreservationsystem.dao.CommentDAO commentDAO = new hotelreservationsystem.dao.CommentDAO();
        
        Report report = new Report("Guest Feedback Report", 
                DateUtil.formatDate(startDate) + " to " + DateUtil.formatDate(endDate));
        
        int totalComments = 0;
        int positiveComments = 0;
        int neutralComments = 0;
        int negativeComments = 0;
        
        // Get comments for all rooms and filter by date
        Room[] rooms = HotelReservationSystem.getRooms();
        int roomCount = HotelReservationSystem.getRoomCount();
        
        for (int i = 0; i < roomCount; i++) {
            Room room = rooms[i];
            if (room != null) {
                hotelreservationsystem.model.Comment[] roomComments = commentDAO.getCommentsByRoom(room.getRoomId());
                
                for (hotelreservationsystem.model.Comment comment : roomComments) {
                    if (comment != null) {
                        Date commentDate = comment.getCommentDate();
                        // Filter by date range
                        if (commentDate != null && 
                            commentDate.compareTo(startDate) >= 0 && 
                            commentDate.compareTo(endDate) <= 0) {
                            
                            totalComments++;
                            
                            // Basic sentiment analysis based on rating
                            int rating = comment.getRating();
                            if (rating >= 4) {
                                positiveComments++;
                            } else if (rating == 3) {
                                neutralComments++;
                            } else {
                                negativeComments++;
                            }
                        }
                    }
                }
            }
        }
        
        // Calculate percentages
        double positivePercentage = totalComments > 0 ? (double) positiveComments / totalComments * 100 : 0;
        double neutralPercentage = totalComments > 0 ? (double) neutralComments / totalComments * 100 : 0;
        double negativePercentage = totalComments > 0 ? (double) negativeComments / totalComments * 100 : 0;
        
        // Add report items
        report.addReportItem("Date Range", DateUtil.formatDate(startDate) + " to " + DateUtil.formatDate(endDate));
        report.addReportItem("Total Feedback Comments", String.valueOf(totalComments));
        report.addReportItem("Positive Feedback (4-5 stars)", String.valueOf(positiveComments) + 
                " (" + String.format("%.2f%%", positivePercentage) + ")");
        report.addReportItem("Neutral Feedback (3 stars)", String.valueOf(neutralComments) + 
                " (" + String.format("%.2f%%", neutralPercentage) + ")");
        report.addReportItem("Negative Feedback (1-2 stars)", String.valueOf(negativeComments) + 
                " (" + String.format("%.2f%%", negativePercentage) + ")");
        
        return report;
    }
    
    /**
     * Generate a monthly comparison report
     * @param startDate Start date for the report
     * @param endDate End date for the report
     * @return Report object containing monthly comparison data
     */
    public static Report generateMonthlyComparisonReport(Date startDate, Date endDate) {
        Booking[] bookings = HotelReservationSystem.getBookings();
        int bookingCount = HotelReservationSystem.getBookingCount();
        
        Report report = new Report("Monthly Revenue Comparison", 
                DateUtil.formatDate(startDate) + " to " + DateUtil.formatDate(endDate));
        
        // Arrays to track monthly bookings and revenue (index 0 = January, 11 = December)
        int[] monthlyBookings = new int[12];
        double[] monthlyRevenue = new double[12];
        
        // Calculate bookings and revenue by month
        for (int i = 0; i < bookingCount; i++) {
            Booking booking = bookings[i];
            if (booking != null && !booking.isCancelled() && booking.isPaid()) {
                Date bookingDate = booking.getCheckInDate();
                // Only count bookings within date range
                if (bookingDate != null && 
                    bookingDate.compareTo(startDate) >= 0 && 
                    bookingDate.compareTo(endDate) <= 0) {
                    
                    // Get month (0-11 for Jan-Dec)
                    int month = DateUtil.getMonth(bookingDate);
                    
                    monthlyBookings[month]++;
                    monthlyRevenue[month] += booking.getTotalAmount();
                }
            }
        }
        
        // Find peak months
        int peakBookingMonth = 0;
        int peakBookingCount = 0;
        int peakRevenueMonth = 0;
        double peakRevenueAmount = 0.0;
        
        for (int i = 0; i < 12; i++) {
            if (monthlyBookings[i] > peakBookingCount) {
                peakBookingCount = monthlyBookings[i];
                peakBookingMonth = i;
            }
            
            if (monthlyRevenue[i] > peakRevenueAmount) {
                peakRevenueAmount = monthlyRevenue[i];
                peakRevenueMonth = i;
            }
        }
        
        // Add report items
        report.addReportItem("Date Range", DateUtil.formatDate(startDate) + " to " + DateUtil.formatDate(endDate));
        
        String[] monthNames = {"January", "February", "March", "April", "May", "June", 
                              "July", "August", "September", "October", "November", "December"};
        
        for (int i = 0; i < 12; i++) {
            if (monthlyBookings[i] > 0) {
                report.addReportItem(monthNames[i] + " Bookings", String.valueOf(monthlyBookings[i]));
                report.addReportItem(monthNames[i] + " Revenue", "$" + String.format("%.2f", monthlyRevenue[i]));
            }
        }
        
        // Add peak months information
        if (peakBookingCount > 0) {
            report.addReportItem("Peak Booking Month", monthNames[peakBookingMonth] + 
                    " (" + peakBookingCount + " bookings)");
        }
        
        if (peakRevenueAmount > 0) {
            report.addReportItem("Peak Revenue Month", monthNames[peakRevenueMonth] + 
                    " ($" + String.format("%.2f", peakRevenueAmount) + ")");
        }
        
        return report;
    }
    
    /**
     * Export a Report to PDF format
     * @param report Report to export
     * @param filePath Path where the PDF should be saved
     * @throws Exception If there's an error creating the PDF
     */
    public static void exportToPdf(Report report, String filePath) throws Exception {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            
            // Add title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph(report.getTitle(), titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            
            // Add generation date
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Paragraph date = new Paragraph("Generated: " + report.getGenerationDate(), normalFont);
            date.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(date);
            
            // Add empty line
            document.add(new Paragraph(" "));
            
            // Add report items
            if (report.getItems().isEmpty()) {
                document.add(new Paragraph("No data available for this report type.", normalFont));
                document.add(new Paragraph("Make sure you have added bookings, rooms, or payments to the system.", normalFont));
            } else {
                for (Report.ReportItem item : report.getItems()) {
                    Paragraph paragraph = new Paragraph(item.getLabel() + ": " + item.getValue(), normalFont);
                    document.add(paragraph);
                }
            }
        } catch (DocumentException | IOException e) {
            throw new Exception("Error creating PDF: " + e.getMessage(), e);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }
    
    /**
     * Export a Report to CSV format
     * @param report Report to export
     * @param filePath Path where the CSV should be saved
     * @throws Exception If there's an error creating the CSV
     */
    public static void exportToCsv(Report report, String filePath) throws Exception {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write header
            writer.append("Report: ").append(report.getTitle()).append("\n");
            writer.append("Generated: ").append(report.getGenerationDate()).append("\n\n");
            
            // Write CSV header
            writer.append("Item,Value\n");
            
            // Write report items
            if (report.getItems().isEmpty()) {
                writer.append("No data,No data available for this report\n");
            } else {
                for (Report.ReportItem item : report.getItems()) {
                    // Escape commas in the label and value
                    String label = item.getLabel().contains(",") ? "\"" + item.getLabel() + "\"" : item.getLabel();
                    String value = item.getValue().contains(",") ? "\"" + item.getValue() + "\"" : item.getValue();
                    
                    writer.append(label).append(",").append(value).append("\n");
                }
            }
            
            writer.flush();
        } catch (IOException e) {
            throw new Exception("Error creating CSV: " + e.getMessage(), e);
        }
    }
}
