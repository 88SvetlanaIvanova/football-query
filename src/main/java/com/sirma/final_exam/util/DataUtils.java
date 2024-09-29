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
/*
The DataUtils class encapsulates utility methods for validating and parsing data, specifically focusing on checking strings for validity, 
parsing dates from various formats, and validating arrays of strings.
DataUtils class serves as a utility class offering methods for string validation, date parsing, and array element validation.
All methods in the class are static, allowing them to be accessed without creating an instance of the DataUtils class.
Regular expression pattern used in the isFaultyString method to determine the validity of a string based on allowed characters.

isFaultyString: Checks if a string contains characters outside the defined pattern.
parseDate: Parses a date string using various date patterns and returns a LocalDate object.
areValidString: Validates an array of strings by checking each element for faulty characters based on the isFaultyString method.
*/
