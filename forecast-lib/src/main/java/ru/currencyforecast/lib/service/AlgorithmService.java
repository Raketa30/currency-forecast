package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.service.algorithm.Algorithm;

import java.util.List;

public interface AlgorithmService {
    List<CurrencyData> getForecastForPeriod(List<CurrencyData> currencyDataList, String period);

    List<CurrencyData> getForecastForDate(List<CurrencyData> currencyDataList, String localDate);

    void setAlgorithm(Algorithm algorithm);

    int getAlgBaseIndex();
}
