package ru.currencyforecast.app.service;

import ru.currencyforecast.app.domain.CurrencyData;
import ru.currencyforecast.app.service.algorithm.AlgorithmService;

import java.util.Collections;
import java.util.List;

import static ru.currencyforecast.app.common.Constant.FORECAST_DEPTH_TOMMOROW;
import static ru.currencyforecast.app.common.Constant.FORECAST_DEPTH_WEEK;

public class AlgorithmServiceImpl implements ForecastService {
    private AlgorithmService algorithmService;

    public AlgorithmServiceImpl(AlgorithmService algorithmService) {
        this.algorithmService = algorithmService;
    }

    @Override
    public List<CurrencyData> getForecast(List<CurrencyData> currencyDataList, String period) {
        if (period.equals(FORECAST_DEPTH_TOMMOROW)) {
            return Collections.singletonList(algorithmService.getTomorrowForecast(currencyDataList));
        } else if (period.equals(FORECAST_DEPTH_WEEK)) {
            return algorithmService.getWeekForecast(currencyDataList);
        }
        return Collections.emptyList();
    }

    @Override
    public void setAlgorithm(AlgorithmService algorithm) {
        this.algorithmService = algorithm;
    }
}
