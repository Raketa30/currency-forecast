package ru.currencyforecast.lib.service;

import com.github.sh0nk.matplotlib4j.Plot;
import ru.currencyforecast.lib.domain.*;
import ru.currencyforecast.lib.domain.response.GraphMessageImpl;
import ru.currencyforecast.lib.domain.response.Response;
import ru.currencyforecast.lib.domain.response.ResponseImpl;
import ru.currencyforecast.lib.domain.response.TextMessageImpl;
import ru.currencyforecast.lib.repository.Repository;
import ru.currencyforecast.lib.service.algorithm.ActualAlgorithmImpl;
import ru.currencyforecast.lib.service.algorithm.AverageAlgorithmImpl;
import ru.currencyforecast.lib.service.algorithm.InternetAlgorithmImpl;
import ru.currencyforecast.lib.service.algorithm.MisticAlgorithmImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.currencyforecast.lib.common.Constant.*;

public class ServiceImpl implements Service {
    private final Repository repository;
    private final AlgorithmService algorithmService;
    private final ImageService imageService;

    public ServiceImpl(Repository repository, AlgorithmService algorithmService, ImageService imageService) {
        this.repository = repository;
        this.algorithmService = algorithmService;
        this.imageService = imageService;
    }

    @Override
    public Response getListForecast(String currency, String period, String algoritm) {
        if (setForcastServiceAlgorithm(algoritm)) {
            List<CurrencyData> dataByAlgorithm = getDataByAlgorithm(currency);
            List<CurrencyData> forecast = algorithmService.getForecastForPeriod(dataByAlgorithm, period);
            return prepareTextResponse(transformToText(forecast));
        } else {
            return prepareTextResponse(MESSAGE_WRONG_ALG + algoritm);
        }
    }

    @Override
    public Response getGraphForecast(List<String> currency, String period, String algorithm) {
        if (setForcastServiceAlgorithm(algorithm)) {
            Map<String, List<CurrencyData>> currencyDataMap = getMultiCurrencyMap(currency, period);
            return prepareImageResponse(imageService.getForecastTrend(currencyDataMap));
        } else {
            return prepareTextResponse(MESSAGE_WRONG_ALG + algorithm);
        }
    }

    private Map<String, List<CurrencyData>> getMultiCurrencyMap(List<String> currency, String period) {
        Map<String, List<CurrencyData>> currencyDataMap = new HashMap<>();
        for (String cur : currency) {
            List<CurrencyData> dataByAlgorithm = getDataByAlgorithm(cur);
            List<CurrencyData> currencyDataList = algorithmService.getForecastForPeriod(dataByAlgorithm, period);
            currencyDataMap.put(cur, currencyDataList);
        }
        return currencyDataMap;
    }

    private Response prepareImageResponse(Plot forecastTrend) {
        return new ResponseImpl(new GraphMessageImpl(forecastTrend), true);
    }

    private boolean setForcastServiceAlgorithm(String algoritm) {
        switch (algoritm) {
            case ALG_ACTUAL:
                algorithmService.setAlgorithm(new ActualAlgorithmImpl());
                return true;
            case ALG_AVG:
                algorithmService.setAlgorithm(new AverageAlgorithmImpl());
                return true;
            case ALG_INTERNET:
                algorithmService.setAlgorithm(new InternetAlgorithmImpl());
                return true;
            case ALG_MISTIC:
                algorithmService.setAlgorithm(new MisticAlgorithmImpl());
                return true;
            default:
                return false;
        }
    }

    private List<CurrencyData> getDataByAlgorithm(String currency) {
        final int algBaseIndex = algorithmService.getAlgBaseIndex();
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


