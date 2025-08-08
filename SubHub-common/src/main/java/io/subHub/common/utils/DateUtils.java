

package io.subHub.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Date processing
 *
 * @author By
 */
public class DateUtils {
    /**Time format (yyyy MM dd)*/
	public final static String DATE_PATTERN = "yyyy-MM-dd";
    /** Time format (yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     *The date format is: yyyy MM dd
     * @param date
     * @return  Date in yyyy MM dd format
     */
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * The date format is: yyyy MM dd
     * @param date
     * @param pattern  Date: dateutils. Date time pattern
     * @return  Return date in yyyy MM dd format
     */
    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * Date resolution
     * @param date
     * @param pattern  Date: dateutils. Date time pattern
     * @return
     */
    public static Date parse(String date, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert string to date
     * @param strDate Date string
     * @param pattern The format of the date, such as: DateUtils DATetiME-PATERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)){
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.parseLocalDateTime(strDate).toDate();
    }

    /**
     * Obtain start and end dates based on the number of weeks
     * @param week  Cycle 0 this week, -1 last week, -2 last week, 1 next week, 2 next week
     *
     * @return  Return date [0] start date, date [1] end date
     */
    public static Date[] getWeekStartAndEnd(int week) {
        DateTime dateTime = new DateTime();
        LocalDate date = new LocalDate(dateTime.plusWeeks(week));

        date = date.dayOfWeek().withMinimumValue();
        Date beginDate = date.toDate();
        Date endDate = date.plusDays(6).toDate();
        return new Date[]{beginDate, endDate};
    }

    /**
     * Add/subtract the [seconds] of a date
     *
     * @param date date
     * @param seconds Seconds, negative numbers subtract
     * @return Date after adding/subtracting a few seconds
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * Add/subtract the minutes of a date
     *
     * @param date date
     * @param minutes Minutes, negative numbers subtract
     * @return Date after adding/subtracting a few minutes
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * Add/subtract the [hour] of the date
     *
     * @param date date
     * @param hours Hours, negative numbers subtract
     * @return Date after adding/subtracting a few hours
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * Add/subtract the [day] of the date
     *
     * @param date date
     * @param days Days, negative numbers subtract
     * @return Date after adding/subtracting a few days
     */
    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * Add/subtract the week of the date
     *
     * @param date date
     * @param weeks Weeks, negative numbers subtract
     * @return Date after adding/subtracting a few weeks
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * Add/subtract the month of the date
     *
     * @param date date
     * @param months Months, negative numbers subtract
     * @return Date after adding/subtracting a few months
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * Add/subtract the year of the date
     *
     * @param date date
     * @param years Years, negative numbers subtract
     * @return Date after adding/subtracting a few years
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }

    /**
     * Obtain the maximum time of a certain day on November 11, 2022 23:59:59
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {

        if (date == null) {
            date = new Date();
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());;
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Obtain the minimum time for a certain day from November 11, 2022 00:00:00
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date) {

        if (date == null) {
            date = new Date();
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

}
