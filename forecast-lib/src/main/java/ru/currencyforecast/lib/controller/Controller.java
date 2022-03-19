package ru.currencyforecast.lib.controller;

import ru.currencyforecast.lib.domain.ForecastRequest;

/**
 * Основной контроллер
 */
public interface Controller {
    /**
     * передача сообщения в Response
     *
     * @param message - текст сообщения
     */
    void addMessage(String message);

    /**
     * Непосредственное выполнение комманды с параметрами:
     *
     * @param request - параметры запроса
     */
    void execute(ForecastRequest request);
}
