package ru.currencyforecast.app.service;

import ru.currencyforecast.app.domain.CurrencyData;
import ru.currencyforecast.app.service.algorithm.AlgorithmService;

import java.util.List;

public interface ForecastService {
    List<CurrencyData> getForecast(List<CurrencyData> currencyDataList, String period);

    void setAlgorithm(AlgorithmService algorithm);
}
