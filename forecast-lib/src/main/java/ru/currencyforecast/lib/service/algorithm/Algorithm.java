package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;

public interface Algorithm {
    List<CurrencyData> getForcastForPeriod(List<CurrencyData> dataFromRepository, int periodDaysIndex);

    int getBaseDaysNumber();
}
