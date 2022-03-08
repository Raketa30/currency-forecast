package ru.currencyforecast.lib.controller;

import ru.currencyforecast.lib.domain.ResponseImpl;
import ru.currencyforecast.lib.domain.TextMessageImpl;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.lib.service.Service;

import java.util.List;

public class BotControllerImpl implements Controller {
    private final DataModel dataModel;
    private final Service service;

    public BotControllerImpl(DataModel dataModel, Service service) {
        this.dataModel = dataModel;
        this.service = service;
    }

    @Override
    public void execute(List<String> currency, String period, String output, String algorithm) {

    }


    @Override
    public void addMessage(String message) {
        dataModel.setResponseData(new ResponseImpl(new TextMessageImpl(message), false));
    }

    private void getTextForecast(String currency, String period, String algorithm) {
        dataModel.setResponseData(service.getForecast(currency, period, algorithm));
    }
}
