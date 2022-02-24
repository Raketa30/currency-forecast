package ru.currencyforecast.app.controller;

import ru.currencyforecast.app.common.Regex;
import ru.currencyforecast.app.domain.Data;
import ru.currencyforecast.app.model.Model;
import ru.currencyforecast.app.service.Service;

import java.util.List;
import java.util.Optional;

import static ru.currencyforecast.app.common.Constant.*;

public class SimpleForcastController implements Controller {
    private final Model model;
    private final Service service;

    public SimpleForcastController(Model model, Service service) {
        this.model = model;
        this.service = service;
    }

    @Override
    public void getForecast(String command) {
        model.clear();
        if (isMatches(command)) {
            String operationalCommand = getOperationlCommand(command, 0);
            String currency = getOperationlCommand(command, 1);
            String period = getOperationlCommand(command, 2);

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

    private String getOperationlCommand(String command, int commandIndex) {
        String[] splitedLine = command.toLowerCase().split("\\s+");
        return splitedLine[commandIndex];
    }
}

