package com.simpledev.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static long getTimeStamp(String timeStr) {
        // 只考虑两种格式 yyyy-MM-dd HH:mm:ss.SSS
        //             yyyy-MM-dd HH:mm:ss
        DateTimeFormatter formatter;
        if (timeStr.indexOf('.') > 0) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        } else {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        }
        LocalDateTime dateTime = LocalDateTime.parse(timeStr, formatter);
        return dateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }
}
