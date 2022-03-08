package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.service.algorithm.Algorithm;

import java.util.Collections;
import java.util.List;

import static ru.currencyforecast.lib.common.Constant.*;

public class AlgorithmServiceImpl implements ForecastService {
    private Algorithm algorithm;

    public AlgorithmServiceImpl(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public List<CurrencyData> getForecast(List<CurrencyData> currencyDataList, String period) {
        switch (period) {
            case FORECAST_DEPTH_TOMMOROW:
                return algorithm.getForecast(currencyDataList, FORECAST_DEPTH_TOMORROW_INDEX);
            case FORECAST_DEPTH_WEEK:
                return algorithm.getForecast(currencyDataList, FORECAST_DEPTH_WEEK_INDEX);
            case FORECAST_DEPTH_MONTH:
                return algorithm.getForecast(currencyDataList, FORECAST_DEPTH_MONTH_INDEX);
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
}
