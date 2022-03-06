package ru.currencyforecast.app.service;

import ru.currencyforecast.app.domain.CurrencyData;
import ru.currencyforecast.app.repository.Repository;

import java.util.List;

import static ru.currencyforecast.app.common.Constant.ALGORITHM_BASE_DAYS;

public class ConsoleServiceImpl implements Service {

    private final Repository repository;
    private final ForecastService forecastService;

    public ConsoleServiceImpl(Repository repository, ForecastService forecastService) {
        this.repository = repository;
        this.forecastService = forecastService;
    }

    @Override
    public List<CurrencyData> getForecast(String currency, String period) {
        List<CurrencyData> currencyData = repository.getCurrencyData(currency, ALGORITHM_BASE_DAYS);
        return forecastService.getForecast(currencyData, period);
    }

}


