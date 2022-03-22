package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Сервис обработки данных и создания трендов
 */
public interface TrendService {
    /**
     * Получение тренда на основе входящих валют
     *
     * @param forecast - карта валют и их данных
     * @return Опциональный файл с изображением
     */
    Optional<File> getForecastTrend(Map<String, List<CurrencyData>> forecast);
}
