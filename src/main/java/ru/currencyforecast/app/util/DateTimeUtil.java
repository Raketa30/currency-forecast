package ru.currencyforecast.app.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DateTimeUtil {
    private static final Map<Integer, String> dayOfWeeks = new HashMap<>();

    static {
        dayOfWeeks.put(1, "Пн");
        dayOfWeeks.put(2, "Вт");
        dayOfWeeks.put(3, "Ср");
        dayOfWeeks.put(4, "Чт");
        dayOfWeeks.put(5, "Пт");
        dayOfWeeks.put(6, "Сб");
        dayOfWeeks.put(7, "Вс");
    }

    private DateTimeUtil() {
    }

    public static String getFormattedWeekdayAndDate(LocalDate localDate) {
        return dayOfWeeks.get(localDate.getDayOfWeek().getValue())
                + " " + localDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
