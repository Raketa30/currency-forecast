package ru.currencyforecast.app.service;

import ru.currencyforecast.app.domain.CurrencyData;

import java.util.List;
import java.util.Optional;

public interface Service {
    Optional<List<CurrencyData>> getForecast(String currency, String period);
}
