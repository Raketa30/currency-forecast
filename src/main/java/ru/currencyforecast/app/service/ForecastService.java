package ru.currencyforecast.app.service;

import ru.currencyforecast.app.domain.Data;
import ru.currencyforecast.app.service.algorithm.SimpleForecastAlgorithm;

import java.util.List;

public interface ForecastService {
    List<Data> getForecast(List<Data> dataList, String period);

    void setAlgorithm(SimpleForecastAlgorithm algorithm);
}
