package ru.currencyforecast.lib.repository;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;
import java.util.Optional;

public interface Repository {
    Optional<CurrencyData> getCurrencyDataByDate(String currency, String date);

    List<CurrencyData> getDataByCdxAndLimitByALgBaseIndex(String currency, int days);

    List<CurrencyData> readAllData(String currency);

}
