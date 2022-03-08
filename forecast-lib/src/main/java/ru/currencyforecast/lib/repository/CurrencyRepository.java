package ru.currencyforecast.lib.repository;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import ru.currencyforecast.lib.domain.CurrencyData;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.currencyforecast.lib.common.Constant.*;

public class CurrencyRepository implements Repository {
    @Override
    public List<CurrencyData> getCurrencyData(String currency, int days) {
        switch (currency) {
            case CURRENCY_EUR:
                return readAndConvertCurrencyData(EUR_CSV_LINK, days);
            case CURRENCY_USD:
                return readAndConvertCurrencyData(USD_CSV_LINK, days);
            case CURRENCY_TRY:
                return readAndConvertCurrencyData(TRY_CSV_LINK, days);
            default:
                return Collections.emptyList();
        }
    }

    private List<CurrencyData> readAndConvertCurrencyData(String currency, int days) {
        List<CurrencyData> currencyDataList = new ArrayList<>();
        try (InputStreamReader inputStreamReader = new InputStreamReader(
                new FileInputStream(
                        Objects.requireNonNull(CurrencyRepository.class.getClassLoader().getResource(currency)).getFile()), CHARSET)
        ) {
            CsvToBean<CurrencyData> csvToBean = new CsvToBeanBuilder<CurrencyData>(inputStreamReader)
                    .withType(CurrencyData.class)
                    .withSeparator(CSV_DELIMETER)
                    .withMultilineLimit(days).build();

            currencyDataList.addAll(csvToBean.parse().stream()
                    .limit(days)
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            //todo logger
        }
        return currencyDataList;
    }

}
