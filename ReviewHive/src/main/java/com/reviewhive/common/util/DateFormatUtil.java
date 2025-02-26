package com.reviewhive.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author Julius P. Basas
 * @added 12/18/2024
 */
public class DateFormatUtil {
	
	private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Formats a given Timestamp to 'yyyy-MM-dd HH:mm:ss'.
     *
     * @param timestamp the Timestamp to format
     * @return the formatted date-time string
     */
    public static Timestamp currentDate() {
    	
    	Timestamp now = new Timestamp(System.currentTimeMillis());
    	
        SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP_FORMAT);
        String formattedDate = formatter.format(now);

        return Timestamp.valueOf(formattedDate);
    }
}
