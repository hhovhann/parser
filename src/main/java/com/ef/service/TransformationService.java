package com.ef.service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


//@Service
public class TransformationService {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String DURATION_TYPE = "hourly";

    /***
     * Parse String to LocalDateTime
     */
    public static LocalDateTime parseToLocalDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    /***
     * Retrieves end date time based on duration and start date time
     */
    public static LocalDateTime retrieveEndDateTime(LocalDateTime startDate, String duration){
        return duration.equals(DURATION_TYPE) ? startDate.plusHours(1L) : startDate.plusHours(24L);
    }
}
