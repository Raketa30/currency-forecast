package ru.currencyforecast.app.repository;

import ru.currencyforecast.app.domain.CurrencyData;

import java.util.List;
import java.util.Optional;

public interface Repository {
    Optional<List<CurrencyData>> getCurrencyData(String currency, int days);
}
