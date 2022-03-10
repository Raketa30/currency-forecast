package ru.currencyforecast.telegram;

import ru.currencyforecast.telegram.factory.BotFactory;

/**
 * Запуск телеграм бота
 */
public class CurrencyBotApp {
    public static void main(String[] args) {
        BotFactory.getCurrencyBot().launch();
    }
}
