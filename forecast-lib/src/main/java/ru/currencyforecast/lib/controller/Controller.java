package ru.currencyforecast.lib.controller;

import java.util.List;

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
     * @param currency - список валют
     * @param period - период или конкретна€ дата
     * @param algorithm - алгоритм прогноза
     * @param output - вывод: лист или график
     */
    void execute(List<String> currency, String period, String algorithm, String output);
}
