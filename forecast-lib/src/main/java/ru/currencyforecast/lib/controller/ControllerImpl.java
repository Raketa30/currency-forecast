package ru.currencyforecast.lib.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.currencyforecast.lib.domain.Request;
import ru.currencyforecast.lib.domain.response.ResponseImpl;
import ru.currencyforecast.lib.domain.response.TextMessageImpl;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.lib.service.Service;

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

        switch (output) {
            case OUTPUT_LIST:
                dataModel.setResponseData(
                        service.getListForecast(
                                request.getCurrencyList().get(0), request.getPeriodOrDate(), request.getAlgorithm())
                );
                break;
            case OUTPUT_GRAPH:
                dataModel.setResponseData(
                        service.getTrendForecast(
                                request.getCurrencyList(), request.getPeriodOrDate(), request.getAlgorithm())
                );
                break;
            default:
                addMessage(MESSAGE_WRONG_OUTPUT + output);
                break;
        }
        log.info("ControllerImpl execute Request: {}", request);
    }


}

