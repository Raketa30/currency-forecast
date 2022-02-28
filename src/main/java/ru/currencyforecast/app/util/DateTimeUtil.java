package ru.currencyforecast.app.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateTimeUtil {
    private DateTimeUtil() {
    }

    public static String getFormattedWeekdayAndDate(LocalDate localDate) {
        return localDate
                .getDayOfWeek()
                .getDisplayName(TextStyle.NARROW, new Locale("ru"))
                + " " + localDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
