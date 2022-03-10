package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;

/**
 * Алгоритм прогноза
 */
public interface Algorithm {
    /**
     * @return количество предыдущих записей, требующихся для прогноза
     */
    int getBaseDaysNumber();

    /**
     * Прогноз для указанного периода в днях
     *
     * @param dataListForAnalisys - входящий список данных
     * @param periodDays          - количество прогнозиемых дней
     * @return список с прогнозом на количсетво дней
     */
    List<CurrencyData> getForcastForPeriod(List<CurrencyData> dataListForAnalisys, int periodDays);
}
