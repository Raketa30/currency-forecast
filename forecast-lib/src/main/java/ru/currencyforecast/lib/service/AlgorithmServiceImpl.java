package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.service.algorithm.Algorithm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static ru.currencyforecast.lib.common.Constant.*;

public class AlgorithmServiceImpl implements AlgorithmService {
    private Algorithm algorithm;

    public AlgorithmServiceImpl(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public List<CurrencyData> getForecastForPeriod(List<CurrencyData> currencyDataList, String period) {
        switch (period) {
            case FORECAST_DEPTH_TOMMOROW:
                return algorithm.getForcastForPeriod(currencyDataList, FORECAST_DEPTH_TOMORROW_INDEX);
            case FORECAST_DEPTH_WEEK:
                return algorithm.getForcastForPeriod(currencyDataList, FORECAST_DEPTH_WEEK_INDEX);
            case FORECAST_DEPTH_MONTH:
                return algorithm.getForcastForPeriod(currencyDataList, FORECAST_DEPTH_MONTH_INDEX);
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public List<CurrencyData> getForecastForDate(List<CurrencyData> currencyDataList, String date) {
        long between = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN)));
        List<CurrencyData> periodList = algorithm.getForcastForPeriod(currencyDataList, (int) between);
        return Collections.singletonList(periodList.get(periodList.size() - 1));
    }

    @Override
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public int getAlgBaseIndex() {
        return algorithm.getBaseDaysNumber();
    }
}
