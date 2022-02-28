package ru.currencyforecast.app.controller;

import ru.currencyforecast.app.model.DataModel;
import ru.currencyforecast.app.service.Service;

import static ru.currencyforecast.app.common.Constant.*;

public class ConsoleControllerImpl implements Controller {
    private final DataModel model;
    private final Service service;

    public ConsoleControllerImpl(DataModel model, Service service) {
        this.model = model;
        this.service = service;
    }

    @Override
    public void execute(String global, String currency, String period) {
        switch (global) {
            case COMMAND_RATE:
                getForcast(currency, period);
                break;
            default:
                model.addMessageAttribute(MESSAGE_WRONG_COMMAND + global + " not found");
        }
    }

    private void getForcast(String currency, String period) {
        service.getForecast(currency, period)
                .ifPresentOrElse(model::addAttribute, () -> model.addMessageAttribute(MESSAGE_EMPTY_DATA));
    }
}

