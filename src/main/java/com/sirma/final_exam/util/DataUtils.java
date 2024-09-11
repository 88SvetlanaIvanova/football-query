package com.sirma.final_exam.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class DataUtils {
    private static final Pattern VALID_PATTERN = Pattern.compile("^[\\p{L} !@#$%^&*()_+={}\\[\\]|\\:;\"'<>,.?/-]*$");

    public static boolean isFaultyString(String value) {
        return !VALID_PATTERN.matcher(value).matches();
    }

    public static LocalDate parseDate(String date) {
        if(date == null || date.isEmpty()){
            throw new IllegalArgumentException("Date string is null or empty");
        }
        List<String> datePatterns = Arrays.asList("M/d/yyyy", "MM/dd/yyyy", "yyyy-MM-dd", "E, MMM dd yyyy",
                "dd.MM.yyyy", "M.d.yyyy", "MM.dd.yyyy", "yyyy.MM.dd");

        for (String datePattern : datePatterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
                return LocalDate.parse(date, formatter);
            } catch (DateTimeParseException e) {
                continue;
            }
        }
        throw new IllegalArgumentException("Failed to parse date: " + date + ". Try another date format.");
    }
    public static boolean areValidString(String[] value) {
        for (int i = 1; i <= 3 ; i++) {
            if(isFaultyString(value[i].trim())){
                return false;
            }
        } return true;
    }
}
