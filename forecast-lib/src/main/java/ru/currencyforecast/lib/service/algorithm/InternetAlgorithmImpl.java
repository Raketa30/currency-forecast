package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;

import static ru.currencyforecast.lib.common.Constant.ALG_INTERNET_BASE;

public class InternetAlgorithmImpl implements Algorithm {
    @Override
    public List<CurrencyData> getForcastForPeriod(List<CurrencyData> dataFromRepository, int periodDaysIndex) {
        return null;
    }

    @Override
    public int getBaseDaysNumber() {
        return ALG_INTERNET_BASE;
    }
}
