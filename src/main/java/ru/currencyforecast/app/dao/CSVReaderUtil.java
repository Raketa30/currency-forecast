package ru.currencyforecast.app.dao;

import ru.currencyforecast.app.domain.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static ru.currencyforecast.app.common.Constant.*;
import static ru.currencyforecast.app.common.Regex.CSV_LINE_MATCHER;

public class CSVReaderUtil {

    private CSVReaderUtil() {
    }

    public static Optional<List<Data>> findLastWeekData(String currency, int daysCount) {
        switch (currency) {
            case EUR:
                return read(EUR_CSV_LINK, daysCount);
            case USD:
                return read(USD_CSV_LINK, daysCount);
            case TRY:
                return read(TRY_CSV_LINK, daysCount);
        }
        return Optional.empty();
    }

    private static Optional<List<Data>> read(String url, int daysCount) {
        List<Data> dataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(url))) {
            int counter = 0;
            while (reader.ready()) {
                if (counter == daysCount) {
                    break;
                }
                String line = reader.readLine();
                if (isMatches(line)) {
                    Data data = getDataFromLine(line);
                    dataList.add(data);
                    counter++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.reverse(dataList);
        return Optional.of(dataList);
    }

    private static boolean isMatches(String line) {
        return line.matches(CSV_LINE_MATCHER);
    }

    private static Data getDataFromLine(String line) {
        String[] args = line.split(";");
        LocalDate date = LocalDate.parse(args[0], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        double cost = Double.parseDouble(args[1].replace(",", "."));

        return Data.builder()
                .setDate(date)
                .setCost(cost)
                .build();
    }
}