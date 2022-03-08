package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.domain.Response;
import ru.currencyforecast.lib.domain.ResponseImpl;
import ru.currencyforecast.lib.domain.TextMessageImpl;
import ru.currencyforecast.lib.repository.Repository;
import ru.currencyforecast.lib.service.algorithm.ActualAlgorithmImpl;
import ru.currencyforecast.lib.service.algorithm.AverageAlgorithmImpl;
import ru.currencyforecast.lib.service.algorithm.InternetAlgorithmImpl;
import ru.currencyforecast.lib.service.algorithm.MisticAlgorithmImpl;

import java.util.List;

import static ru.currencyforecast.lib.common.Constant.*;

public class ConsoleServiceImpl implements Service {
    private final Repository repository;
    private final ForecastService forecastService;

    public ConsoleServiceImpl(Repository repository, ForecastService forecastService) {
        this.repository = repository;
        this.forecastService = forecastService;
    }

    @Override
    public Response getForecast(String currency, String period, String algoritm) {
        if (!setAlgorithm(algoritm)) {
            return prepareTextResponse(MESSAGE_WRONG_ALG + algoritm);
        }

        List<CurrencyData> currencyData = repository.getCurrencyData(currency, ALGORITHM_BASE_DAYS);
        if (currencyData.isEmpty()) {
            return prepareTextResponse(MESSAGE_EMPTY_DATA);
        }

        List<CurrencyData> forecast = forecastService.getForecast(currencyData, period);
        return prepareTextResponse(transformToText(forecast));
    }

    private boolean setAlgorithm(String algoritm) {
        switch (algoritm) {
            case ALG_ACTUAL:
                forecastService.setAlgorithm(new ActualAlgorithmImpl());
                return true;
            case ALG_AVG:
                forecastService.setAlgorithm(new AverageAlgorithmImpl());
                return true;
            case ALG_INTERNET:
                forecastService.setAlgorithm(new InternetAlgorithmImpl());
                return true;
            case ALG_MISTIC:
                forecastService.setAlgorithm(new MisticAlgorithmImpl());
                return true;
            default:
                return false;
        }
    }

    private Response prepareTextResponse(String message) {
        return new ResponseImpl(new TextMessageImpl(message), false);
    }

    private String transformToText(List<CurrencyData> forecast) {
        StringBuilder stringBuilder = new StringBuilder();
        forecast.forEach(currencyData -> stringBuilder.append(currencyData).append("\n"));
        return stringBuilder.toString();
    }
}


