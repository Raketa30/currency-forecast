package ru.currencyforecast.lib.cli;

/**
 * Обработчик командной строки
 */
public interface Commander {
    /**
     * @param command - введеная пользователем команда
     */
    void execute(String command);
}
