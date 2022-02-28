package ru.currencyforecast.app.repository;

import ru.currencyforecast.app.domain.CurrencyData;
import ru.currencyforecast.app.util.CSVReaderUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.currencyforecast.app.common.Constant.*;

public class CurrencyRepository implements Repository {
    @Override
    public Optional<List<CurrencyData>> getCurrencyData(String currency, int days) {
        switch (currency) {
            case CURRENCY_EUR:
                return Optional.of(readAndConvertCurrencyData(EUR_CSV_LINK, days));
            case CURRENCY_USD:
                return Optional.of(readAndConvertCurrencyData(USD_CSV_LINK, days));
            case CURRENCY_TRY:
                return Optional.of(readAndConvertCurrencyData(TRY_CSV_LINK, days));
            default:
                return Optional.empty();
        }
    }

    private List<CurrencyData> readAndConvertCurrencyData(String currency, int days) {
        return CSVReaderUtil.read(currency, days)
                .stream()
                .map(this::getDataFromLine)
                .collect(Collectors.toList());

    }

    private CurrencyData getDataFromLine(String dataLineFromCsv) {
        String[] splitedLineByDelimeter = dataLineFromCsv.split(CSV_DELIMETER);
        LocalDate date = LocalDate.parse(splitedLineByDelimeter[0], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        double cost = Double.parseDouble(splitedLineByDelimeter[1].replace(",", "."));
        String title = splitedLineByDelimeter[2];

        return CurrencyData.builder()
                .setDate(date)
                .setCost(cost)
                .setTitle(title)
                .build();
    }
}
