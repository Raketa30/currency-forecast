package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.Request;
import ru.currencyforecast.lib.domain.response.Response;

/**
 * Основной сервис приложения ?? - Переделать в один метод возвращающий  респонс с типизированным сообщением
 */
public interface Service {
    /**
     * метод возвращающий ответ прогнозом в виде графика
     *
     * @param request - запрос прогноза
     * @return Response
     */
    Response getForecast(Request request);
}
