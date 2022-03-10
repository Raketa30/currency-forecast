package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.domain.response.GraphMessageImpl;
import ru.currencyforecast.lib.domain.response.Response;
import ru.currencyforecast.lib.domain.response.ResponseImpl;
import ru.currencyforecast.lib.domain.response.TextMessageImpl;
import ru.currencyforecast.lib.repository.Repository;
import ru.currencyforecast.lib.service.algorithm.ActualAlgorithmImpl;
import ru.currencyforecast.lib.service.algorithm.AverageAlgorithmImpl;
import ru.currencyforecast.lib.service.algorithm.InternetAlgorithmImpl;
import ru.currencyforecast.lib.service.algorithm.MisticAlgorithmImpl;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.currencyforecast.lib.common.Constant.*;

public class ServiceImpl implements Service {
    private final Repository repository;
    private final ForecastService forecastService;
    private final TrendService trendService;

    public ServiceImpl(Repository repository, ForecastService forecastService, TrendService trendService) {
        this.repository = repository;
        this.forecastService = forecastService;
        this.trendService = trendService;
    }

    @Override
    public Response getListForecast(String currency, String period, String algoritm) {
        if (setForcastServiceAlgorithm(algoritm)) {
            List<CurrencyData> dataByAlgorithm = getDataByAlgorithm(currency);
            List<CurrencyData> forecast = forecastService.getForecastForPeriod(dataByAlgorithm, period);
            return prepareTextResponse(transformToText(forecast));
        } else {
            return prepareTextResponse(MESSAGE_WRONG_ALG + algoritm);
        }
    }

    @Override
    public Response getGraphForecast(List<String> currency, String period, String algorithm) {
        if (setForcastServiceAlgorithm(algorithm)) {
            Map<String, List<CurrencyData>> currencyDataMap = getMultiCurrencyMap(currency, period);
            return prepareImageResponse(trendService.getForecastTrend(currencyDataMap));
        } else {
            return prepareTextResponse(MESSAGE_WRONG_ALG + algorithm);
        }
    }

    private Map<String, List<CurrencyData>> getMultiCurrencyMap(List<String> currency, String period) {
        Map<String, List<CurrencyData>> currencyDataMap = new HashMap<>();
        for (String cur : currency) {
            List<CurrencyData> dataByAlgorithm = getDataByAlgorithm(cur);
            List<CurrencyData> currencyDataList = forecastService.getForecastForPeriod(dataByAlgorithm, period);
            currencyDataMap.put(cur, currencyDataList);
        }
        return currencyDataMap;
    }

    private Response prepareImageResponse(Path forecastTrend) {
        return new ResponseImpl(new GraphMessageImpl(forecastTrend), true);
    }

    private boolean setForcastServiceAlgorithm(String algoritm) {
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

    private List<CurrencyData> getDataByAlgorithm(String currency) {
        final int algBaseIndex = forecastService.getAlgBaseIndex();
        return repository.getDataByCdxAndLimitByALgBaseIndex(currency, algBaseIndex);
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


