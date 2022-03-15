package ru.currencyforecast.lib.domain;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.*;
import ru.currencyforecast.lib.util.DateTimeUtil;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class CurrencyData {
    @CsvBindByName(locale = "ru-RU")
    private int nominal;

    @CsvBindByName
    @CsvDate("dd.MM.yyyy")
    private LocalDate data;

    @CsvBindByName(locale = "ru-RU")
    private double curs;

    @CsvBindByName
    private String cdx;

    @Override
    public String toString() {
        return String.format("%s - %.2f", DateTimeUtil.getFormattedWeekdayAndDate(data), curs);
    }
}
