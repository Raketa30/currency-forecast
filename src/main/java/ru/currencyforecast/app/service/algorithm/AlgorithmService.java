package ru.currencyforecast.app.service.algorithm;

import ru.currencyforecast.app.domain.CurrencyData;

import java.util.List;

public interface AlgorithmService {
    CurrencyData getTomorrowForecast(List<CurrencyData> allByCurrency);

    List<CurrencyData> getWeekForecast(List<CurrencyData> allByCurrency);
}
