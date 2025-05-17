/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author user
 */
public class DateUtil {
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * Parse a date string to a Date object
     * @param dateStr Date in yyyy-MM-dd format
     * @return Parsed Date object
     * @throws ParseException if the date string is invalid
     */
    public static Date parseDate(String dateStr) throws ParseException {
        return DATE_FORMAT.parse(dateStr);
    }
    
    /**
     * Format a Date object to a string
     * @param date Date to format
     * @return Formatted date string in yyyy-MM-dd format
     */
    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }
    
    /**
     * Calculate the number of days between two dates
     * @param startDate Start date
     * @param endDate End date
     * @return Number of days between the dates
     */
    public static int getDaysBetween(Date startDate, Date endDate) {
        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
    
    /**
     * Check if a date is in the future
     * @param date Date to check
     * @return true if the date is in the future, false otherwise
     */
    public static boolean isFutureDate(Date date) {
        Date today = new Date();
        return date.after(today);
    }
    
    /**
     * Check if end date is after start date
     * @param startDate Start date
     * @param endDate End date
     * @return true if end date is after start date, false otherwise
     */
    public static boolean isValidDateRange(Date startDate, Date endDate) {
        return endDate.after(startDate);
    }
    
    /**
     * Get today's date formatted as string
     * @return Today's date in yyyy-MM-dd format
     */
    public static String getTodayAsString() {
        return formatDate(new Date());
    }
}
