package ru.currencyforecast.lib.repository;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.util.DateTimeUtil;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static ru.currencyforecast.lib.common.Constant.*;

/**
 * Имплементация репозитория, доступ к данным CSV файлов
 */
@Slf4j
public class CsvCurrencyRepository implements Repository {
    private final Map<String, String> currencyUrlsMap;

    public CsvCurrencyRepository() {
        this.currencyUrlsMap = new HashMap<>();
        currencyUrlsMap.put(CURRENCY_EUR, EUR_CSV_LINK);
        currencyUrlsMap.put(CURRENCY_USD, USD_CSV_LINK);
        currencyUrlsMap.put(CURRENCY_TRY, TRY_CSV_LINK);
        currencyUrlsMap.put(CURRENCY_BGN, BGN_CSV_LINK);
        currencyUrlsMap.put(CURRENCY_AMD, AMD_CSV_LINK);
    }

    @Override
    public List<CurrencyData> getAllDataByCdx(String cdx) {
        if (currencyUrlsMap.containsKey(cdx)) {
            return getAllData(currencyUrlsMap.get(cdx));
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<CurrencyData> getDataByCdxAndDate(String cdx, String date) {
        if (currencyUrlsMap.containsKey(cdx)) {
            return getDataByDate(currencyUrlsMap.get(cdx), date);
        }
        log.info("CurrencyRepository getDataByCdxAndDate Empty Optional");
        return Optional.empty();
    }

    @Override
    public List<CurrencyData> getDataByCdxAndLimitByALgBaseIndex(String cdx, int algBaseIndex) {
        if (currencyUrlsMap.containsKey(cdx)) {
            return getDataListByDays(currencyUrlsMap.get(cdx), algBaseIndex);
        }
        log.info("CurrencyRepository  getDataByCdxAndLimitByALgBaseIndex Empty List");
        return Collections.emptyList();
    }

    @Override
    public List<CurrencyData> getDataByCdxFromDate(String cdx, String date) {
        if (currencyUrlsMap.containsKey(cdx)) {
            return getDataFromDate(currencyUrlsMap.get(cdx), date);
        }
        log.info("CurrencyRepository  getDataByCdxFromDate Empty List");
        return Collections.emptyList();
    }

    /**
     * @param fileDataUrl - url csv файла
     * @param date        - дата
     * @return список значений от указанной даты
     */
    private List<CurrencyData> getDataFromDate(String fileDataUrl, String date) {
        LocalDate fromDate = DateTimeUtil.getLocalDate(date);
        return getAllData(fileDataUrl)
                .stream()
                .filter(currencyData -> DateTimeUtil.isEqualOrAfter(currencyData.getData(), fromDate))
                .collect(Collectors.toList());
    }

    /**
     * @param fileDataUrl - url csv файла
     * @param days        - количество дней
     * @return список значений за количество дней
     */
    private List<CurrencyData> getDataListByDays(String fileDataUrl, int days) {
        List<CurrencyData> currencyDataList = new ArrayList<>();
        try (InputStreamReader inputStreamReader = getInputStreamReader(fileDataUrl)) {
            currencyDataList.addAll(
                    getDataCsvToBean(inputStreamReader)
                            .parse()
                            .stream()
                            .limit(days)
                            .collect(Collectors.toList())
            );
        } catch (IOException e) {
            log.info("CurrencyRepository getDataListByDays IOexception: {}", e.getMessage());
        }
        return currencyDataList;
    }

    /**
     * @param fileDataUrl - url csv файла
     * @param date        - дата
     * @return опциональное значение за конкретную дату
     */
    private Optional<CurrencyData> getDataByDate(String fileDataUrl, String date) {
        LocalDate localDate = DateTimeUtil.getLocalDate(date);
        try (InputStreamReader inputStreamReader = getInputStreamReader(fileDataUrl)) {
            return getDataCsvToBean(inputStreamReader)
                    .stream()
                    .filter(currencyData -> currencyData.getData().isEqual(localDate))
                    .findFirst();
        } catch (IOException e) {
            log.info("CurrencyRepository getDataByDate IOexception: {}", e.getMessage());
        }
        log.info("CurrencyRepository getDataByDate Empty Optional");
        return Optional.empty();
    }

    /**
     * @param fileDataUrl - url csv файла
     * @return список всех значений из файла
     */
    private List<CurrencyData> getAllData(String fileDataUrl) {
        List<CurrencyData> currencyDataList = new ArrayList<>();
        try (InputStreamReader inputStreamReader = getInputStreamReader(fileDataUrl)) {
            currencyDataList.addAll(getDataCsvToBean(inputStreamReader).parse());
        } catch (IOException e) {
            log.info("CurrencyRepository getAllData IOexception: {}", e.getMessage());
        }
        return currencyDataList;
    }

    /**
     * @param fileDataUrl - url csv файла
     * @return InputStream из указанного файла
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    private InputStreamReader getInputStreamReader(String fileDataUrl) throws UnsupportedEncodingException, FileNotFoundException {
        return new InputStreamReader(
                new FileInputStream(
                        Objects.requireNonNull(CsvCurrencyRepository.class.getClassLoader().getResource(fileDataUrl))
                                .getFile()), CHARSET);
    }

    /**
     * @return CsvToBean
     */
    private CsvToBean<CurrencyData> getDataCsvToBean(InputStreamReader inputStreamReader) {
        return new CsvToBeanBuilder<CurrencyData>(inputStreamReader)
                .withType(CurrencyData.class)
                .withSeparator(CSV_DELIMETER)
                .build();
    }
}

