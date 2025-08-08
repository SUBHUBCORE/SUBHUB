package io.subHub.common.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * date conversion
 *
 * @author By
 */
@Component
public class DateConverter implements Converter<String, Date> {
    private static final Logger logger = LoggerFactory.getLogger(DateConverter.class);
    private static List<String> formatList = new ArrayList<>(5);
    static {
        formatList.add("yyyy-MM");
        formatList.add("yyyy-MM-dd");
        formatList.add("yyyy-MM-dd HH:mm");
        formatList.add("yyyy-MM-dd HH:mm:ss");
        formatList.add("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    }

    @Override
    public Date convert(String source) {
        String value = source.trim();
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        if(source.matches("^\\d{4}-\\d{1,2}$")){
            return parseDate(source, formatList.get(0));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
            return parseDate(source, formatList.get(1));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")){
            return parseDate(source, formatList.get(2));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")){
            return parseDate(source, formatList.get(3));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}.*T.*\\d{1,2}:\\d{1,2}:\\d{1,2}.*..*$")){
            return parseDate(source, formatList.get(4));
        } else {
            throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
        }
    }

    /**
     * format date
     * @param dateStr String Character date
     * @param format String format
     * @return Date date
     */
    public Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
            logger.error("Formatted date with date: {} and format : {} ", dateStr, format);
        }
        return date;
    }

    /**
     * @param creationTime
     * @param days
     * @return
     */
    public Date expirationTimeByDays(Date creationTime, int days) {
        LocalDateTime expirationTime = creationTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        expirationTime = expirationTime.plusDays(days);
        return Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static boolean isValidDateFormat(String dateStr,String format) {
        try {
            YearMonth.parse(dateStr, DateTimeFormatter.ofPattern(format));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
