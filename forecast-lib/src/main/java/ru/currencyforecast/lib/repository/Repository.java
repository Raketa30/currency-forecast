package ru.currencyforecast.lib.repository;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;

public interface Repository {
    List<CurrencyData> getCurrencyData(String currency, int days);
}
