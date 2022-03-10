package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * —ервис обработки данных и создани€ трендов
 */
public interface TrendService {
    /**
     * ѕолучение тренда на основе вход€щих валют
     *
     * @param forecast - карта валют и их данных
     * @return ссылку на сохраненное изображение
     */
    Path getForecastTrend(Map<String, List<CurrencyData>> forecast);
}
