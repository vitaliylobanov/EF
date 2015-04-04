package com.ecofactorqa.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class DateUtil.
 * @author $Author: vraj $
 * @version $Rev: 33535 $ $Date: 2015-01-22 10:43:39 +0530 (Thu, 22 Jan 2015) $
 */
public final class DateUtil {

    public static final String DATE_ONLY_FMT = "MM/dd/yyyy";
    public static final String DATE_FMT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FMT_FULL = "yyyy-MM-dd HH:mm:ss aa";
    public static final String DATE_FMT_FULL_TZ = "yyyy-MM-dd HH:mm:ss aa Z";
    public static final String DATE_MONTH_YEAR_FMT = "d MMMM yyyy";
    public static final String MM_DD_YY = "MM/dd/yy HH:mm aa";
    public static final String LARGE_FORMAT = "EEEE, MMMM dd, yyyy HH:mm:ss aa";
    public static final String HOUR_FORMAT = "HH:mm:ss aa";
    private static Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    /**
     * Instantiates a new date util.
     */
    private DateUtil() {

    }

    /**
     * Gets the current month.
     * @return the current month
     */
    public static String getCurrentMonth() {

        final String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };

        final Calendar cal = Calendar.getInstance();
        String month = "";
        month = months[cal.get(Calendar.MONTH)];
        return month;
    }

    /**
     * Gets the current date.
     * @return the current date
     */
    public static int getCurrentDate() {

        final Calendar now = Calendar.getInstance();
        int date = 0;
        date = now.get(Calendar.DATE);
        LOGGER.debug("Current  Date : " + date);
        return date;
    }

    /**
     * Gets the current year.
     * @return the current year
     */
    public static int getCurrentYear() {

        final Calendar now = Calendar.getInstance();
        int year = 0;
        year = now.get(Calendar.YEAR);
        return year;
    }

    /**
     * Gets the last date of month.
     * @return the last date of month
     */
    public static Map<String, Integer> getLastDateOfMonth() {

        final HashMap<String, Integer> monthMap = new HashMap<String, Integer>();
        monthMap.put("January", 31);
        monthMap.put("February", 28);
        monthMap.put("March", 31);
        monthMap.put("April", 30);
        monthMap.put("May", 31);
        monthMap.put("June", 30);
        monthMap.put("July", 31);
        monthMap.put("August", 31);
        monthMap.put("September", 30);
        monthMap.put("October", 31);
        monthMap.put("November", 30);
        monthMap.put("December", 31);

        return monthMap;
    }

    /**
     * Gets the calendar for specified date.
     * @param dateTime the date time
     * @return the calendar for specified date
     * @throws ParseException the parse exception
     */
    public static Calendar getCalendarForSpecifiedDate(final String dateTime) throws ParseException {

        final Calendar calTime = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FMT_FULL, Locale.ENGLISH);
        final Date date = (Date) sdf.parse(dateTime);
        calTime.setTime(date);
        return calTime;
    }

    /**
     * Difference in minutes.
     * @param date1 the date1
     * @param date2 the date2
     * @return the long
     */
    public static long differenceInMinutes(final Date date1, final Date date2) {

        final long diff = date2.getTime() - date1.getTime();
        return diff / (60 * 1000);
    }

    /**
     * Gets the calendar.
     * @param timeStamp the time stamp
     * @param dateFormat the date format
     * @return the calendar
     * @throws ParseException the parse exception
     */
    public static Calendar getCalendar(String timeStamp, final String dateFormat) throws ParseException {

        DateTime dt = new DateTime(DateTimeZone.UTC);
        Calendar calendar = dt.toCalendar(Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FMT_FULL, Locale.ENGLISH);
        Date date = sdf.parse(timeStamp);
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Gets the time stamp.
     * @param calendar the calendar
     * @return the time stamp
     */
    public static String getTimeStamp(Calendar calendar) {

        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FMT_FULL, Locale.ENGLISH);
        timeStamp = sdf.format(calendar.getTime());
        LOGGER.debug("Timestamp " + timeStamp);
        return timeStamp;
    }

    /**
     * Gets the current time stamp.
     * @return the current time stamp
     */
    public static String getUTCCurrentTimeStamp() {

        DateTime startDate = new DateTime(DateTimeZone.UTC);
        DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_FMT_FULL);
        return fmt.print(startDate);
    }

    /**
     * Gets the uTC current time stamp.
     * @param pattern the pattern
     * @return the uTC current time stamp
     */
    public static String getUTCCurrentTimeStamp(String pattern) {

        DateTime startDate = new DateTime(DateTimeZone.UTC);
        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.print(startDate);
    }

    /**
     * Gets the current time stamp for zone.
     * @param timeZone the time zone
     * @param pattern the pattern
     * @return the current time stamp for zone
     */
    public static String getCurrentTimeStampForZone(String timeZone, String pattern) {

        DateTime startDate = new DateTime(DateTimeZone.forID(timeZone));
        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.print(startDate);
    }

    /**
     * Gets the calendar.
     * @param startTime the start time
     * @param minutes the minutes
     * @return the calendar
     */
    public static Calendar getCalendar(Calendar startTime, int minutes) {

        Calendar endTime = null;
        DateTime start = new DateTime(startTime);
        DateTime end = start.plusMinutes(minutes);
        DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_FMT_FULL);
        fmt = fmt.withZoneUTC();
        endTime = parseToUTCCalendar(fmt.print(end), DATE_FMT_FULL);
        return endTime;
    }

    /**
     * Checks if is last date.
     * @return true, if is last date
     */
    public static boolean isLastDate() {

        DateTime startDate = new DateTime(DateTimeZone.UTC);
        int max = startDate.dayOfMonth().getMaximumValue();
        int current = startDate.getDayOfMonth();
        return current == max;
    }

    /**
     * Gets the local calendar.
     * @param timeZone the time zone
     * @return the local calendar
     * @throws ParseException the parse exception
     */
    public static Calendar getLocalCalendar(final String timeZone) throws ParseException {

        DateTime dateTime = new DateTime(DateTimeZone.forID(timeZone));
        Calendar tzCalendar = dateTime.toCalendar(Locale.ENGLISH);
        return tzCalendar;
    }

	/**
	 * Gets the uTC calendar.
	 *
	 * @param localCalendar the local calendar
	 * @return the uTC calendar
	 */
	public static Calendar getUTCCalendar(Calendar localCalendar) {
		Calendar UTCCalendar = null;
		UTCCalendar = parseToUTCCalendar(format(localCalendar, DATE_FMT_FULL_TZ), DATE_FMT_FULL_TZ);
		return UTCCalendar;
	}

    /**
     * Gets the uTC time from local.
     * @param localTimeInMillis the local time in millis
     * @param timeZone the time zone
     * @return the uTC time from local
     */
    public static long getUTCTimeFromLocal(long localTimeInMillis, String timeZone) {

        System.out.println("Time Zone 2 = " + timeZone);
        long utcTime = 0;
        DateTimeZone dateTimeZone = DateTimeZone.forID(timeZone);
        utcTime = dateTimeZone.convertLocalToUTC(localTimeInMillis, false);
        System.out.println(dateTimeZone);
        return utcTime;
    }

    /**
     * To calendar.
     * @param date the date
     * @return the calendar
     */
    public static Calendar convertDatetoCalendar(final Date date) {

        Calendar calendar = getUTCCalendar();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * To date.
     * @param calendar the calendar
     * @return the date
     * @throws ParseException the parse exception
     */
    public static Date toDate(final Calendar calendar) throws ParseException {

        Date date = new Date();
        date = calendar.getTime(); 
        return date;
    }

    /**
     * To date with timezone.
     * @param dateValue the date value
     * @return the date
     * @throws ParseException the parse exception
     */
    public static Date toDateWithTimezone(String dateValue) throws ParseException {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FMT_FULL_TZ, Locale.ENGLISH);
        date = sdf.parse(dateValue);
        return date;
    }

    /**
     * String to calendar.
     * @param strDate the str date
     * @param timezone the timezone
     * @return the calendar
     * @throws ParseException the parse exception
     */
    public static Calendar stringToCalendar(String strDate, TimeZone timezone) throws ParseException {

        String FORMAT_DATETIME = "yyyy-MM-dd'T'HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME, Locale.ENGLISH);
        sdf.setTimeZone(timezone);
        Date date = sdf.parse(strDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * Format.
     * @param calendar the calendar
     * @param pattern the pattern
     * @return the string
     */
    public static String formatToUTC(Calendar calendar, String pattern) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        fmt = fmt.withZoneUTC();
        String strDate = fmt.print(new DateTime(calendar));
        LOGGER.debug("UTC Timestamp " + strDate);
        return strDate;
    }

    /**
     * Format.
     * @param date the date
     * @param pattern the pattern
     * @return the string
     */
    public static String formatToUTC(Date date, String pattern) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        fmt = fmt.withZoneUTC();
        String strDate = fmt.print(new DateTime(date));
        return strDate;
    }

    /**
     * Format.
     * @param calendar the calendar
     * @param pattern the pattern
     * @return the string
     */
    public static String format(Calendar calendar, String pattern) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        String strDate = fmt.print(new DateTime(calendar));
        return strDate;
    }

    /**
     * Copy to utc.
     * @param calendar the calendar
     * @return the calendar
     */
    public static Calendar copyToUTC(Calendar calendar) {

        Calendar aCalendar = null;
        String date = format(calendar, DATE_FMT);
        aCalendar = parseToUTCCalendar(date, DATE_FMT);
        return aCalendar;
    }

    /**
     * Format.
     * @param date the date
     * @param pattern the pattern
     * @return the string
     */
    public static String format(Date date, String pattern) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        String strDate = fmt.print(new DateTime(date));
        LOGGER.debug("Timestamp " + strDate);
        return strDate;
    }

    /**
     * Format.
     * @param milliseconds the milliseconds
     * @param pattern the pattern
     * @return the string
     */
    public static String formatToUTC(Long milliseconds, String pattern) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        fmt = fmt.withZoneUTC();
        String strDate = fmt.print(new DateTime(milliseconds));
        return strDate;
    }

    /**
     * Format.
     * @param timestamp the timestamp
     * @param pattern the pattern
     * @return the string
     */
    public static Calendar parseToUTCCalendar(String timestamp, String pattern) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        fmt = fmt.withZoneUTC();
        DateTime dt = fmt.parseDateTime(timestamp);
        dt = dt.toDateTime(DateTimeZone.UTC);
        return dt.toCalendar(Locale.ENGLISH);
    }

    /** 
     * Parses the to zone calendar.
     * @param timestamp the timestamp
     * @param pattern the pattern
     * @param timeZone the time zone
     * @return the calendar
     */
    public static Calendar parseToZoneCalendar(String timestamp, String pattern, String timeZone) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        fmt = fmt.withZoneUTC();
        DateTime dt = fmt.parseDateTime(timestamp);
        dt = dt.toDateTime(DateTimeZone.forID(timeZone));
        return dt.toCalendar(Locale.ENGLISH);
    }

    /**
     * Format.
     * @param timestamp the timestamp
     * @param pattern the pattern
     * @return the string
     */
    public static Calendar parseToCalendar(String timestamp, String pattern) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        DateTime dt = fmt.parseDateTime(timestamp);
        return dt.toCalendar(Locale.ENGLISH);
    }

    /**
     * Parses the to date.
     * @param timestamp the timestamp
     * @param pattern the pattern
     * @return the date
     */
    public static Date parseToDate(String timestamp, String pattern) {

        Date date = null;
        try {
            date = new SimpleDateFormat(pattern, Locale.ENGLISH).parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Format.
     * @param timestamp the timestamp
     * @param pattern the pattern
     * @return the string
     */
    public static Date parseToUTCDate(String timestamp, String pattern) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        fmt = fmt.withZoneUTC();
        Date date = fmt.parseDateTime(timestamp).toDate();
        return date;
    }

    /**
     * Parses the to ms.
     * @param timestamp the timestamp
     * @param pattern the pattern
     * @return the long
     */
    public static Long parseToUTCMS(String timestamp, String pattern) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        fmt = fmt.withZoneUTC();
        Date date = fmt.parseDateTime(timestamp).toDate();
        return date.getTime();
    }

    /**
     * Gets the uTC calendar.
     * @return the uTC calendar
     */
    public static Calendar addTimeToUTCCalendar() {

        long ts = System.currentTimeMillis();
        Date localTime = new Date(ts);
        String format = "yyyy/MM/dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat (format);

        // Convert Local Time to UTC 
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gmtTime = new Date(sdf.format(localTime));
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(gmtTime);                
        return calendar;
    }
    
    /**
     * Gets the UTC calendar.
     * @return the UTC calendar
     */
    public static Calendar getUTCCalendar() {

        DateTime dt = new DateTime(DateTimeZone.UTC);
        Calendar utcCalendar = dt.toCalendar(Locale.ENGLISH);
        LOGGER.debug("Current UTC Date " + formatToUTC(utcCalendar, DATE_FMT_FULL_TZ));        
        return utcCalendar;
    }
   
    /**
     * Gets the uTC date.
     * @return the uTC date
     */
    public static Date getUTCDate() {

        DateTime dt = new DateTime(DateTimeZone.UTC);
        Date utcDate = null;
        try {
            utcDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dt.toString("yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LOGGER.debug("Current UTC Date " + formatToUTC(utcDate, DATE_FMT_FULL_TZ));
        return utcDate;
    }

    /**
     * Gets the uTC milliseconds.
     * @return the uTC milliseconds
     */
    public static Long getUTCMilliseconds() {

        DateTime dt = new DateTime(DateTimeZone.UTC);
        Long utcMS = dt.toDate().getTime();
        LOGGER.debug("Current UTC ms " + utcMS);
        return utcMS;
    }

    /**
     * Gets the uTC day of week.
     * @return the uTC day of week
     */
    public static int getUTCDayOfWeek() {

        DateTime dt = new DateTime(DateTimeZone.UTC);
        int dayOfWeek = dt.getDayOfWeek();
        if (dayOfWeek < 7) {
            dayOfWeek += 1;
        } else if (dayOfWeek == 7) {
            dayOfWeek = 1;
        }
        return dayOfWeek;
    }

    /**
     * Convert time to utc calendar.
     * @param date the date
     * @return the calendar
     */
    public static Calendar convertTimeToUTCCalendar(DateTime date) {

        Calendar utcCalendar = getUTCCalendar();
        
        StringBuilder startDate = new StringBuilder();
        startDate.append(utcCalendar.get(Calendar.YEAR)).append("-").append(utcCalendar.get(Calendar.MONTH) + 1).append("-")
                .append(utcCalendar.get(Calendar.DATE)).toString();
        int hours = date.getHourOfDay();
        int mins = date.getMinuteOfHour();        
        int secs = date.getSecondOfMinute();        
        startDate.append(" " + hours + ":" + mins + ":" + secs);        
        Calendar changedCalendar = parseToUTCCalendar(startDate.toString(), DATE_FMT);        
        System.out.println(changedCalendar);
        return changedCalendar;
    }

    /**
     * Gets the time zone diff.
     * @param timeZone the time zone
     * @return the time zone diff
     */
    public static String getTimeZoneDiff(String timeZone) {

        DateTime startDate = new DateTime(DateTimeZone.forID(timeZone));
        DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_FMT_FULL_TZ);
        String timeval = fmt.print(startDate);
        int lastIndexOfSpace = timeval.lastIndexOf(" ");
        timeval = timeval.substring(lastIndexOfSpace + 1, timeval.length());
        return timeval;
    }

    /**
     * Convert time to utc calendar. For Example: Time Should be 4:0:00 and timeZone Like :
     * America/Los_angeles. It will return the current Date UTC time.
     *
     * @param time the time
     * @param timeZone the time zone
     * @return the calendar
     */
    public static Calendar getUTCCalendar(String time, String timeZone) {

        String utcCalendar = DateUtil.getUTCCurrentTimeStamp();
        Calendar startDateCal = DateUtil.parseToUTCCalendar(utcCalendar, DateUtil.DATE_FMT_FULL);
        int minutesDiff = TimeZone.getTimeZone(timeZone).getOffset(startDateCal.getTimeInMillis());
        String date = new StringBuilder().append(startDateCal.get(Calendar.YEAR)).append("-").append(startDateCal.get(Calendar.MONTH) + 1)
                .append("-").append(startDateCal.get(Calendar.DATE)).toString();
        Calendar calendar = getJobCalendar(time, date, timeZone);
        calendar.add(Calendar.MILLISECOND, -(minutesDiff));
        String timeZoneCal = DateUtil.format(calendar, DateUtil.DATE_FMT_FULL);
        Calendar calendarUTC = DateUtil.parseToUTCCalendar(timeZoneCal, DateUtil.DATE_FMT_FULL);
        return calendarUTC;
    }

    /**
     * Gets the time zone calendar.[Will return the Calendar object for the time and timezone]
     * @param time the time
     * @param timeZone the time zone
     * @return the time zone calendar
     */
    public static Calendar getTimeZoneCalendar(String time, String timeZone) {

    	String zoneCalendar = DateUtil.getCurrentTimeStampForZone(timeZone, DateUtil.DATE_FMT_FULL_TZ);

        Calendar startDateCal = DateUtil.parseToZoneCalendar(zoneCalendar, DateUtil.DATE_FMT_FULL_TZ, timeZone);

        String date = new StringBuilder().append(startDateCal.get(Calendar.YEAR)).append("-").append(startDateCal.get(Calendar.MONTH) + 1)
                .append("-").append(startDateCal.get(Calendar.DATE)).toString();
        Calendar calendar = getJobCalendar(time, date, timeZone);

        return calendar;
    }


    /**
     * Gets the UTC time zone calendar.
     *
     * @param time the time
     * @return the UTC time zone calendar
     */
    public static Calendar getUTCTimeZoneCalendar(String time) {

        Calendar startDateCal = DateUtil.getUTCCalendar();

        String date = new StringBuilder().append(startDateCal.get(Calendar.YEAR)).append("-").append(startDateCal.get(Calendar.MONTH) + 1)
                .append("-").append(startDateCal.get(Calendar.DATE)).toString();
        Calendar calendar = getJobCalendar(time, date, "UTC");
        
        return calendar;
    }

    /**
     * Gets the current time zone calendar.
     * @param timeZone the time zone
     * @return the current time zone calendar
     */
    public static Calendar getCurrentTimeZoneCalendar(String timeZone) {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        return calendar;
    }

    /**
     * Convert any date spo time to utc calendar. For Example: Time Should be 4:0:00 and timeZone
     * Like : America/Los_angeles and Any Calendar, which is for any date. It will return the
     * current Date UTC time.
     *
     * @param time the time
     * @param timeZone the time zone
     * @param anyCalendar the any calendar
     * @return the calendar
     */
    public static Calendar getUTCCalendar(String time, String timeZone, Calendar anyCalendar) {


        String utcCalendar = DateUtil.getUTCCurrentTimeStamp();
        Calendar startDateCal = DateUtil.parseToUTCCalendar(utcCalendar, DateUtil.DATE_FMT_FULL);
        int minutesDiff = TimeZone.getTimeZone(timeZone).getOffset(startDateCal.getTimeInMillis());

        String date = new StringBuilder().append(anyCalendar.get(Calendar.YEAR)).append("-").append(anyCalendar.get(Calendar.MONTH) + 1).append("-")
                .append(anyCalendar.get(Calendar.DATE)).toString();
        Calendar calendar = getJobCalendar(time, date, timeZone);
        calendar.add(Calendar.MILLISECOND, -(minutesDiff));
        String timeZoneCal = DateUtil.format(calendar, DateUtil.DATE_FMT_FULL);
        Calendar calendarUTC = DateUtil.parseToUTCCalendar(timeZoneCal, DateUtil.DATE_FMT_FULL);
        return calendarUTC;
    }

    /**
     * Gets the job calendar.
     * @param time the time
     * @param date the date
     * @param timeZone the time zone
     * @return the job calendar
     */
    private static Calendar getJobCalendar(final String time, final String date, String timeZone) {

        String timeZoneDiff = DateUtil.getTimeZoneDiff(timeZone);
        final StringBuilder changedTime = new StringBuilder(date);
        changedTime.append(" ").append(time)
                .append(Integer.parseInt(time.substring(0, time.indexOf(':'))) > 12 ? " PM " + timeZoneDiff : " AM " + timeZoneDiff);
        Calendar cal = null;
        try {
            cal = DateUtil.parseToZoneCalendar(changedTime.toString(), DateUtil.DATE_FMT_FULL_TZ, timeZone);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return cal;
    }

    /**
     * Convert joda timezone.
     * @param date the date
     * @param srcTimeZone the src time zone
     * @param destTimeZone the dest time zone
     * @return the date
     */
    public static Date convertTimezone(LocalDateTime date, String srcTimeZone, String destTimeZone) {

        DateTime srcDateTime = date.toDateTime(DateTimeZone.forID(srcTimeZone));
        DateTime dstDateTime = srcDateTime.withZone(DateTimeZone.forID(destTimeZone));
        return dstDateTime.toLocalDateTime().toDateTime().toDate();
    }

    /**
     * Parses the date with timezone.
     * @param dateValue the date value
     * @return the date
     * @throws ParseException the parse exception
     */
    public static Date parseDateWithTimezone(String dateValue) throws ParseException {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_MONTH_YEAR_FMT, Locale.ENGLISH);
        date = sdf.parse(dateValue);
        return date;
    }
    
    /**
     * Add days to today.
     * @return string
     * @throws ParseException the parse exception
     */
    public static String addDaysToToday() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+'00:00'");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, 2); // Adding 2 days
        String output = sdf.format(c.getTime()); 
        System.out.println(output);
        return output;
    }
    
    /**
     * Add 5 min to today for away.
     * @return string
     * @throws ParseException the parse exception
     */
    public static String addFiveMinToToday() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+'00:00'");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, 2); // Adding 2 days
        String output = sdf.format(c.getTime()); 
        System.out.println(output);
        return output;
    }
    
    /**
     * Subtract given minutes/hours/seconds to actual calendar time .
     * @param calendarTime the calendar time of hours/minutes/seconds
     * @param actualValue the value
     * @return long
     */
    public static long subtractFromUTCMilliSeconds(int calendarTime, int actualValue){
    	
    	final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));    	
    	calendar.add(calendarTime, -actualValue);
    	return calendar.getTimeInMillis();
    }    
    
    /**
     * Add given minutes/hours/seconds to actual calendar time.
     * @param calendarTime the calendar time of hours/minutes/seconds
     * @param actualValue the value
     * @return long
     */
    public static long addToUTCMilliSeconds(int calendarTime, int actualValue){
    	
    	final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));    	
    	calendar.add(calendarTime, actualValue);
    	return calendar.getTimeInMillis();
    }
    
    /**
     * Format to zone.
     * @param milliseconds the milliseconds
     * @param zone the zone
     * @param pattern the pattern
     * @return the string
     */
    public static String formatToZone(Long milliseconds, DateTimeZone zone, String pattern) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        fmt = fmt.withZone(zone);
        String strDate = fmt.print(new DateTime(milliseconds));
        return strDate;
    }
}
