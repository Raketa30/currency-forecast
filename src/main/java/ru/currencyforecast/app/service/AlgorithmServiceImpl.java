package ru.currencyforecast.app.service;

import ru.currencyforecast.app.domain.Data;
import ru.currencyforecast.app.service.algorithm.ForecastAlgorithm;

import java.util.Collections;
import java.util.List;

import static ru.currencyforecast.app.common.Constant.TOMORROW;
import static ru.currencyforecast.app.common.Constant.WEEK;

public class AlgorithmServiceImpl implements ForecastService {
    private ForecastAlgorithm forecastAlgorithm;

    public AlgorithmServiceImpl(ForecastAlgorithm forecastAlgorithm) {
        this.forecastAlgorithm = forecastAlgorithm;
    }

    @Override
    public List<Data> getForecast(List<Data> dataList, String period) {
        if (period.equals(TOMORROW)) {
            return Collections.singletonList(forecastAlgorithm.getTomorrowForecast(dataList));

        } else if (period.equals(WEEK)) {
            return forecastAlgorithm.getWeekForecast(dataList);
        }

        return Collections.emptyList();
    }

    @Override
    public void setAlgorithm(ForecastAlgorithm algorithm) {
        this.forecastAlgorithm = algorithm;
    }
}
