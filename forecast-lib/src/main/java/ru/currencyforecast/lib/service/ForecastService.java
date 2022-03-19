package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Сервис прогнозов
 */
public interface ForecastService {
    /**
     * @return базовый индекс алгоритма
     */
    int getAlgBaseIndex();

    /**
     * Получение прогноза для определенной даты
     *
     * @param incomingDataList - входящий список данных для анализа
     * @param date             - конкретная дата
     * @return список спрогнозированных данных
     */
   Optional<CurrencyData> getForecastForDate(List<CurrencyData> incomingDataList, LocalDate date);

    /**
     * @param incomingDataList - входящий список данных для анализа
     * @param period           - требуемы период прогноза
     * @return список спрогнозированных данных
     */
    List<CurrencyData> getForecastForPeriod(List<CurrencyData> incomingDataList, String period);

    /**
     * Установка требуемого алгоритма прогноза
     *
     * @param algorithm - алгоритм прогноза
     * @return false если отсутствует переданный алгоритм
     */
    boolean setAlgorithm(String algorithm);
}
