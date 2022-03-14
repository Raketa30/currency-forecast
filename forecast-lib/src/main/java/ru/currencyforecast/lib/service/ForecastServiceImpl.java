package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.service.algorithm.*;
import ru.currencyforecast.lib.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.*;

import static ru.currencyforecast.lib.common.Constant.*;

public class ForecastServiceImpl implements ForecastService {
    private final Map<String, Algorithm> algorithmMap;
    private final Map<String, Integer> periodIndexMap;
    private Algorithm algorithm;

    public ForecastServiceImpl() {
        this.algorithmMap = new HashMap<>();
        this.algorithmMap.put(ALG_ACTUAL, new ActualAlgorithm());
        this.algorithmMap.put(ALG_AVG, new AverageAlgorithm());
        this.algorithmMap.put(ALG_MISTIC, new MisticAlgorithm());
        this.algorithmMap.put(ALG_INTERNET, new InternetAlgorithm());

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
    public Optional<CurrencyData> getForecastForDate(List<CurrencyData> incomingDataList, LocalDate date) {
        List<CurrencyData> forcastForPeriod = algorithm.getForcastForPeriod(incomingDataList, (int) DateTimeUtil.daysBetweenFromNowToDate(date));
        CurrencyData last = forcastForPeriod.get(forcastForPeriod.size() - 1);
        return Optional.of(last);
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
