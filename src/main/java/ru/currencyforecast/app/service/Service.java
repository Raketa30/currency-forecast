package ru.currencyforecast.app.service;

import ru.currencyforecast.app.domain.CurrencyData;

import java.util.List;

public interface Service {
    List<CurrencyData> getForecast(String currency, String period);
}
