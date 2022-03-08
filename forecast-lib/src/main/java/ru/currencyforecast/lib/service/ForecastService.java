package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.service.algorithm.Algorithm;

import java.util.List;

public interface ForecastService {
    List<CurrencyData> getForecast(List<CurrencyData> currencyDataList, String period);

    void setAlgorithm(Algorithm algorithm);
}
