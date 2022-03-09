package ru.currencyforecast.lib.repository;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import ru.currencyforecast.lib.domain.CurrencyData;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static ru.currencyforecast.lib.common.Constant.*;

public class CurrencyRepository implements Repository {
    private final Map<String, String> currencyUrlsMap;

    public CurrencyRepository() {
        this.currencyUrlsMap = new HashMap<>();
        currencyUrlsMap.put(CURRENCY_EUR, EUR_CSV_LINK);
        currencyUrlsMap.put(CURRENCY_USD, USD_CSV_LINK);
        currencyUrlsMap.put(CURRENCY_TRY, TRY_CSV_LINK);
        currencyUrlsMap.put(CURRENCY_BGN, BGN_CSV_LINK);
        currencyUrlsMap.put(CURRENCY_AMD, AMD_CSV_LINK);
    }

    @Override
    public Optional<CurrencyData> getCurrencyDataByDate(String currency, String date) {
        if (currencyUrlsMap.containsKey(currency)) {
            return getDataByDate(currencyUrlsMap.get(currency), date);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<CurrencyData> getDataByCdxAndLimitByALgBaseIndex(String currency, int days) {
        if (currencyUrlsMap.containsKey(currency)) {
            return getDataListByDays(currencyUrlsMap.get(currency), days);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<CurrencyData> readAllData(String currency) {
        if (currencyUrlsMap.containsKey(currency)) {
            return getAllData(currencyUrlsMap.get(currency));
        } else {
            return Collections.emptyList();
        }
    }

    private List<CurrencyData> getAllData(String fileDataUrl) {
        List<CurrencyData> currencyDataList = new ArrayList<>();
        try {
            readCsvAndGetDataBeans(fileDataUrl).parse();
        } catch (IOException e) {
            //todo logger
        }
        return currencyDataList;
    }

    private List<CurrencyData> getDataListByDays(String fileDataUrl, int days) {
        List<CurrencyData> currencyDataList = new ArrayList<>();
        try {
            CsvToBean<CurrencyData> csvToBean = readCsvAndGetDataBeans(fileDataUrl);
            currencyDataList.addAll(csvToBean.parse().stream()
                    .limit(days)
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            //todo logger
        }
        return currencyDataList;
    }

    private Optional<CurrencyData> getDataByDate(String fileUrl, String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
        try {
            CsvToBean<CurrencyData> csvToBean = readCsvAndGetDataBeans(fileUrl);
            return csvToBean.stream()
                    .filter(currencyData -> currencyData.getData().isEqual(localDate))
                    .findFirst();
        } catch (IOException e) {
            //todo logger
        }
        return Optional.empty();
    }

    private CsvToBean<CurrencyData> readCsvAndGetDataBeans(String fileDataUrl) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(
                Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileDataUrl)).getFile()), CHARSET
        );
        CsvToBean<CurrencyData> csvToBean = new CsvToBeanBuilder<CurrencyData>(inputStreamReader)
                .withType(CurrencyData.class)
                .withSeparator(CSV_DELIMETER)
                .build();
        inputStreamReader.close();
        return csvToBean;
    }
}
