package ru.currencyforecast.lib.controller;

import java.util.List;

public interface Controller {
    void execute(List<String> currency, String period, String output, String algorithm);

    void addMessage(String message);
}
