package ru.currencyforecast.lib.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Утилитный класс для работы с датой
 */
public class DateTimeUtil {
    private DateTimeUtil() {
    }

    public static String getFormattedWeekdayAndDate(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("E dd.MM.yyyy"));
    }
}
