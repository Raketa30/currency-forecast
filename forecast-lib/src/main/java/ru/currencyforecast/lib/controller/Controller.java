package ru.currencyforecast.lib.controller;

import ru.currencyforecast.lib.domain.Request;

/**
 * ќсновной контроллер
 */
public interface Controller {
    /**
     * передача сообщени€ в Response
     * @param message - текст сообщени€
     */
    void addMessage(String message);

    /**
     * Ќепосредственное выполнение комманды с параметрами:
        @param request - параметры запроса
     */
    void execute(Request request);
}
