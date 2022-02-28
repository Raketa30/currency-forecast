package ru.currencyforecast.app.service;

import ru.currencyforecast.app.domain.CurrencyData;
import ru.currencyforecast.app.repository.Repository;

import java.util.List;
import java.util.Optional;

import static ru.currencyforecast.app.common.Constant.ALGORITHM_BASE_DAYS;

public class ConsoleServiceImpl implements Service {

    private final Repository repository;
    private final ForecastService forecastService;

    public ConsoleServiceImpl(Repository repository, ForecastService forecastService) {
        this.repository = repository;
        this.forecastService = forecastService;
    }

    @Override
    public Optional<List<CurrencyData>> getForecast(String currency, String period) {
        return repository.getCurrencyData(currency, ALGORITHM_BASE_DAYS)
                .map(dataList -> forecastService.getForecast(dataList, period));
    }

}


