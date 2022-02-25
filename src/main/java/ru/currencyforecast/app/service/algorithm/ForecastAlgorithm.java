package ru.currencyforecast.app.service.algorithm;

import ru.currencyforecast.app.domain.Data;

import java.util.List;

public interface ForecastAlgorithm {
    Data getTomorrowForecast(List<Data> allByCurrency);

    List<Data> getWeekForecast(List<Data> allByCurrency);
}
