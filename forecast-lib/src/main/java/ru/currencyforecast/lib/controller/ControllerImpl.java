package ru.currencyforecast.lib.controller;

import ru.currencyforecast.lib.domain.response.ResponseImpl;
import ru.currencyforecast.lib.domain.response.TextMessageImpl;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.lib.service.Service;

import java.util.List;

import static ru.currencyforecast.lib.common.Constant.*;

public class ControllerImpl implements Controller {
    private final DataModel dataModel;
    private final Service service;

    public ControllerImpl(DataModel dataModel, Service service) {
        this.dataModel = dataModel;
        this.service = service;
    }

    @Override
    public void addMessage(String message) {
        dataModel.setResponseData(new ResponseImpl(new TextMessageImpl(message), false));
    }

    @Override
    public void execute(List<String> currency, String period, String algorithm, String output) {
        switch (output) {
            case OUTPUT_GRAPH:
                service.getGraphForecast(currency, period, algorithm);
                break;
            case OUTPUT_LIST:
                if (currency.size() > 1) {
                    addMessage(MESSAGE_MULTICURRENCY_IN_OUTPUT_LIST);
                    break;
                }
                service.getListForecast(currency.get(0), period, algorithm);
                break;
            default:
                addMessage(MESSAGE_WRONG_OUTPUT + output);
        }

    }
}

