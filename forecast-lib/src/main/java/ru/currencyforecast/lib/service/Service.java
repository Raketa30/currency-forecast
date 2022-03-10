package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.response.Response;

import java.util.List;

/**
 * Основной сервис приложения ?? - Переделать в один метод возвращающий  респонс с типизированным сообщением
 */
public interface Service {
    /**
     * метод возвращающий ответ прогнозом в виде списка
     *
     * @param currency  - валюта
     * @param period    - указанный период
     * @param algroithm - алгоритм
     * @return Response
     */
    Response getListForecast(String currency, String period, String algroithm);

    /**
     * метод возвращающий ответ прогнозом в виде графика
     *
     * @param currencyList - валюта
     * @param period       - указанный период
     * @param algroithm    - алгоритм
     * @return Response
     */
    Response getTrendForecast(List<String> currencyList, String period, String algroithm);
}
