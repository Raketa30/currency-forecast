package ru.currencyforecast.lib.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.currencyforecast.lib.domain.Request;
import ru.currencyforecast.lib.domain.response.ResponseImpl;
import ru.currencyforecast.lib.domain.message.TextMessageImpl;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.lib.service.Service;

import java.util.List;

import static ru.currencyforecast.lib.common.Constant.*;

@Slf4j
@RequiredArgsConstructor
public class ControllerImpl implements Controller {
    private final DataModel dataModel;
    private final Service service;

    @Override
    public void addMessage(String message) {
        dataModel.setResponseData(new ResponseImpl(new TextMessageImpl(message), false));
        log.info("Controller: added message to model: {}", message);
    }

    @Override
    public void execute(Request request) {
        String output = request.getOutput();
        List<String> currencyList = request.getCurrencyList();
        String periodOrDate = request.getPeriodOrDate();
        String algorithm = request.getAlgorithm();
        switch (output) {
            case OUTPUT_LIST:
                dataModel.setResponseData(service.getListForecast(currencyList.get(0), periodOrDate, algorithm));
                break;
            case OUTPUT_GRAPH:
                dataModel.setResponseData(service.getTrendForecast(currencyList, periodOrDate, algorithm));
                break;
            default:
                addMessage(MESSAGE_WRONG_OUTPUT + output);
                break;
        }
        log.info("ControllerImpl execute Request: {}", request);
    }


}

