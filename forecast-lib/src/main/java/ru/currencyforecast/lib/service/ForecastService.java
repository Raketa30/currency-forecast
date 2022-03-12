package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;

/**
 * —ервис прогнозов
 */
public interface ForecastService {
    /**
     * @return базовый индекс алгоритма
     */
    int getAlgBaseIndex();

    /**
     * ѕолучение прогноза дл€ определенной даты
     *
     * @param incomingDataList - вход€щий список данных дл€ анализа
     * @param date             - конкретна€ дата
     * @return список спрогнозированных данных
     */
    List<CurrencyData> getForecastForDate(List<CurrencyData> incomingDataList, String date);

    /**
     * @param incomingDataList - вход€щий список данных дл€ анализа
     * @param period           - требуемы период прогноза
     * @return список спрогнозированных данных
     */
    List<CurrencyData> getForecastForPeriod(List<CurrencyData> incomingDataList, String period);

    /**
     * ”становка требуемого алгоритма прогноза
     *
     * @param algorithm
     * @return false если отсутствует переданный алгоритм
     */
    boolean setAlgorithm(String algorithm);
}
