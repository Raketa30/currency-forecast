package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.service.algorithm.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.currencyforecast.lib.common.Constant.*;

public class ForecastServiceImpl implements ForecastService {
    private final Map<String, Algorithm> algorithmMap;
    private final Map<String, Integer> periodIndexMap;
    private Algorithm algorithm;

    public ForecastServiceImpl() {
        this.algorithmMap = new HashMap<>();
        this.algorithmMap.put(ALG_ACTUAL, new ActualAlgorithmImpl());
        this.algorithmMap.put(ALG_AVG, new AverageAlgorithmImpl());
        this.algorithmMap.put(ALG_MISTIC, new MisticAlgorithmImpl());
        this.algorithmMap.put(ALG_INTERNET, new InternetAlgorithmImpl());

        this.periodIndexMap = new HashMap<>();
        this.periodIndexMap.put(FORECAST_DEPTH_TOMMOROW, FORECAST_DEPTH_TOMORROW_INDEX);
        this.periodIndexMap.put(FORECAST_DEPTH_WEEK, FORECAST_DEPTH_WEEK_INDEX);
        this.periodIndexMap.put(FORECAST_DEPTH_MONTH, FORECAST_DEPTH_MONTH_INDEX);
    }

    @Override
    public int getAlgBaseIndex() {
        return algorithm.getBaseDaysNumber();
    }

    @Override
    public List<CurrencyData> getForecastForDate(List<CurrencyData> incomingDataList, String date) {
        long between = ChronoUnit.DAYS.between(
                LocalDate.now(), LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN))
        );
        List<CurrencyData> periodList = algorithm.getForcastForPeriod(incomingDataList, (int) between);
        return Collections.singletonList(periodList.get(periodList.size() - 1));
    }

    @Override
    public List<CurrencyData> getForecastForPeriod(List<CurrencyData> incomingDataList, String period) {
        if (periodIndexMap.containsKey(period)) {
            return algorithm.getForcastForPeriod(incomingDataList, periodIndexMap.get(period));
        }
        return Collections.emptyList();
    }

    @Override
    public boolean setAlgorithm(String algorithm) {
        if (this.algorithmMap.containsKey(algorithm)) {
            this.algorithm = this.algorithmMap.get(algorithm);
            return true;
        }
        return false;
    }
}
