package ru.currencyforecast.lib.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.currencyforecast.lib.domain.Request;
import ru.currencyforecast.lib.domain.message.TextMessage;
import ru.currencyforecast.lib.domain.response.ForecastResponse;
import ru.currencyforecast.lib.domain.response.ResponseType;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.lib.service.Service;

@Slf4j
@RequiredArgsConstructor
public class ControllerImpl implements Controller {
    private final DataModel dataModel;
    private final Service service;

    @Override
    public void addMessage(String message) {
        dataModel.setResponseData(new ForecastResponse<>(ResponseType.TEXT, new TextMessage(message)));
        log.info("Controller: added message to model: {}", message);
    }

    @Override
    public void execute(Request request) {
        dataModel.setResponseData(service.getForecast(request));
        log.info("ControllerImpl execute Request: {}", request);
    }
}

