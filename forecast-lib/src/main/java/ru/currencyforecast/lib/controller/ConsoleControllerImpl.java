package ru.currencyforecast.lib.controller;

import ru.currencyforecast.lib.common.Constant;
import ru.currencyforecast.lib.domain.ResponseImpl;
import ru.currencyforecast.lib.domain.TextMessageImpl;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.lib.service.Service;

import java.util.List;

public class ConsoleControllerImpl implements Controller {
    private final DataModel dataModel;
    private final Service service;

    public ConsoleControllerImpl(DataModel dataModel, Service service) {
        this.dataModel = dataModel;
        this.service = service;
    }

    @Override
    public void execute(List<String> currency, String period, String output, String algorithm) {
        if(output.equals(Constant.OUTPUT_GRAPH) || currency.size() > 1) {
            addMessage(Constant.MESSAGE_WRONG_COMMAND);
        } else {
            final int firstCurrencyIndex = 0;
            getTextForecast(currency.get(firstCurrencyIndex), period, algorithm);
        }

    }

    @Override
    public void addMessage(String message) {
        dataModel.setResponseData(new ResponseImpl(new TextMessageImpl(message), false));
    }

    private void getTextForecast(String currency, String period, String algorithm) {
        dataModel.setResponseData(service.getForecast(currency, period, algorithm));
    }
}

