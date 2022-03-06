package ru.currencyforecast.app.service.algorithm;

import ru.currencyforecast.app.domain.CurrencyData;

import java.util.List;

public interface AlgorithmService {
    List<CurrencyData> getForecast(List<CurrencyData> allByCurrency, int days);
}
