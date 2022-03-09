package ru.currencyforecast.lib.service;

import com.github.sh0nk.matplotlib4j.Plot;
import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;
import java.util.Map;

public interface ImageService {
    Plot getForecastTrend(Map<String, List<CurrencyData>> forecast);
}
