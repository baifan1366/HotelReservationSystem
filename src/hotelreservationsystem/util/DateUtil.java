/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author user
 */
public class DateUtil {
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    
    // Default check-in time: 15:00 (3:00 PM)
    public static final int DEFAULT_CHECKIN_HOUR = 15;
    public static final int DEFAULT_CHECKIN_MINUTE = 0;
    
    // Default check-out time: 12:00 (noon)
    public static final int DEFAULT_CHECKOUT_HOUR = 12;
    public static final int DEFAULT_CHECKOUT_MINUTE = 0;
    
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
     * Create a Date with check-in time (3:00 PM)
     * @param dateStr Date in yyyy-MM-dd format
     * @return Date object with check-in time set
     * @throws ParseException if the date string is invalid
     */
    public static Date parseCheckInDate(String dateStr) throws ParseException {
        Date date = DATE_FORMAT.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, DEFAULT_CHECKIN_HOUR);
        calendar.set(Calendar.MINUTE, DEFAULT_CHECKIN_MINUTE);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    /**
     * Create a Date with check-out time (12:00 PM)
     * @param dateStr Date in yyyy-MM-dd format
     * @return Date object with check-out time set
     * @throws ParseException if the date string is invalid
     */
    public static Date parseCheckOutDate(String dateStr) throws ParseException {
        Date date = DATE_FORMAT.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, DEFAULT_CHECKOUT_HOUR);
        calendar.set(Calendar.MINUTE, DEFAULT_CHECKOUT_MINUTE);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
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
     * Format a Date object to a string with time
     * @param date Date to format
     * @return Formatted date string in yyyy-MM-dd HH:mm format
     */
    public static String formatDateTime(Date date) {
        return DATE_TIME_FORMAT.format(date);
    }
    
    /**
     * Calculate the number of days between two dates
     * @param startDate Start date
     * @param endDate End date
     * @return Number of days between the dates
     */
    public static int getDaysBetween(Date startDate, Date endDate) {

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        endCal.set(Calendar.HOUR_OF_DAY, 0);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND, 0);
        
        long diffInMillis = endCal.getTimeInMillis() - startCal.getTimeInMillis();
        int days = (int) (diffInMillis / (1000 * 60 * 60 * 24));
        
        int stayDays = days;
              
        return stayDays;
    }
    
    /**
     * Check if a date is today or in the future
     * @param date Date to check
     * @return true if the date is today or in the future, false otherwise
     */
    public static boolean isFutureDate(Date date) {
        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        today.setTime((today.getTime() / 1000) * 1000);
        return date.after(today) || isSameDay(date, today);
    }
    
    /**
     * Check if two dates represent the same day
     * @param date1 First date
     * @param date2 Second date
     * @return true if both dates represent the same day, false otherwise
     */
    private static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
               cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * Check if end date is after or equal to start date
     * @param startDate Start date
     * @param endDate End date
     * @return true if end date is after or the same as start date, false otherwise
     */
    public static boolean isValidDateRange(Date startDate, Date endDate) {
        // 不再允许同一天入住和退房，必须至少相差一天
        return endDate.after(startDate) && !isSameDay(startDate, endDate);
    }
    
    /**
     * Get today's date formatted as string
     * @return Today's date in yyyy-MM-dd format
     */
    public static String getTodayAsString() {
        return formatDate(new Date());
    }
}
