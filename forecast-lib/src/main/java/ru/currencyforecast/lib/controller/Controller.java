package ru.currencyforecast.lib.controller;

import java.util.List;

public interface Controller {
    void addMessage(String message);

    void execute(List<String> currency, String period, String algorithm, String output);
}
