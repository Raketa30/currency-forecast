package ru.currencyforecast.app.repository;

import ru.currencyforecast.app.domain.CurrencyData;

import java.util.List;

public interface Repository {
    List<CurrencyData> getCurrencyData(String currency, int days);
}
