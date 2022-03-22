package ru.currencyforecast.console.app;

import ru.currencyforecast.console.app.factory.ConsoleAppFactory;

public class CurrencyForecastApp {
    public static void main(String[] args) {
        ConsoleAppFactory.getConsoleView().launchConsole();
    }
}
