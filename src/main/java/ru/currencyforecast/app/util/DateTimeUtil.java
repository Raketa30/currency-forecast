package ru.currencyforecast.app.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private DateTimeUtil() {
    }

    public static String getFormattedWeekdayAndDate(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("Ee dd.MM.yyyy"));
    }
}
