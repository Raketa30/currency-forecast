package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.ForecastRequest;
import ru.currencyforecast.lib.domain.response.Response;

/**
 * ќсновной сервис приложени€
 */
public interface Service {
    /**
     * метод возвращающий ответ прогнозом в виде графика
     *
     * @param request - запрос прогноза
     * @return Response
     */
    Response getForecastResponse(ForecastRequest request);
}
