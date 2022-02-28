package ru.currencyforecast.app.controller;

import java.util.Map;

public interface Controller {
    void execute(String global, String currency, String period);
}
