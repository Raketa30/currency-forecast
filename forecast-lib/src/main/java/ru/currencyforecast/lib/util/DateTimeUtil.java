package ru.currencyforecast.lib.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static ru.currencyforecast.lib.common.Constant.DATE_FORMAT_PATTERN;

/**
 * Утилитный класс для работы с датой
 */
public class DateTimeUtil {
    private DateTimeUtil() {
    }

    public static String getFormattedWeekdayAndDate(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("E " + DATE_FORMAT_PATTERN));
    }

    public static boolean isDate(String date) {
        try {
            new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isPast(String date) {
        return daysBetweenFromNowToDate(getLocalDate(date)) <= 0;
    }

    public static long daysBetweenFromNowToDate(LocalDate date) {
        return ChronoUnit.DAYS.between(
                LocalDate.now(), date);
    }

    public static LocalDate getLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
    }
}
