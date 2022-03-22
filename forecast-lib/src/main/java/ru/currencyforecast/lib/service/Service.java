package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.ForecastRequest;
import ru.currencyforecast.lib.domain.response.Response;

/**
 * Основной сервис приложения
 */
public interface Service {
    /**
     * метод возвращающий ответ прогноза
     *
     * @param request - запрос прогноза
     * @return Response
     */
    Response getForecastResponse(ForecastRequest request);
}
