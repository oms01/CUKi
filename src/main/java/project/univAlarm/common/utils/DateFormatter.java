package project.univAlarm.common.utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    public static String currentTimeFormatted(){
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss.SSS"));
    }
}
