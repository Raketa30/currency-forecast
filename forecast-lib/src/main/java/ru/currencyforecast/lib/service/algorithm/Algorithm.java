package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;

public interface Algorithm {
    List<CurrencyData> getForecast(List<CurrencyData> allByCurrency, int days);
}
