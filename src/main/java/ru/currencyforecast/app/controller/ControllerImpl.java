package ru.currencyforecast.app.controller;

import ru.currencyforecast.app.common.Regex;
import ru.currencyforecast.app.domain.Data;
import ru.currencyforecast.app.model.Model;
import ru.currencyforecast.app.service.Service;

import java.util.List;
import java.util.Optional;

import static ru.currencyforecast.app.common.Constant.*;

public class ControllerImpl implements Controller {
    private final Model model;
    private final Service service;

    public ControllerImpl(Model model, Service service) {
        this.model = model;
        this.service = service;
    }

    @Override
    public void getForecast(String command) {
        if (isMatches(command)) {

            String operationalCommand = getOperationlCommand(command);
            String currency = getCurrency(command);
            String period = getPeriod(command);

            if (operationalCommand.equals(RATE)) {
                Optional<List<Data>> optionalForecastData = service.getRateByCurrencyAndPeriod(currency, period);
                optionalForecastData.ifPresentOrElse(dataList -> model.addAttribute(RATE, dataList),
                        () -> model.addMessageAttribute(EMPTY_DATA));
            }
        } else {
            model.addMessageAttribute(WRONG_COMMAND);
        }
    }

    private boolean isMatches(String command) {
        return command.matches(Regex.COMMAND_MATCHER);
    }

    private String getOperationlCommand(String command) {
        String[] splitedLine = command.toLowerCase().split("\\s+");
        return splitedLine[0];
    }

    private String getCurrency(String command) {
        String[] splitedLine = command.toLowerCase().split("\\s+");
        return splitedLine[1].toUpperCase();
    }

    private String getPeriod(String command) {
        String[] splitedLine = command.toLowerCase().split("\\s+");
        return splitedLine[2];
    }
}

