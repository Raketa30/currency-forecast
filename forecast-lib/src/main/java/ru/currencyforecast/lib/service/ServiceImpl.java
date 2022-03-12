package ru.currencyforecast.lib.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.domain.Request;
import ru.currencyforecast.lib.domain.message.DataMessage;
import ru.currencyforecast.lib.domain.message.ImageMessage;
import ru.currencyforecast.lib.domain.message.TextMessage;
import ru.currencyforecast.lib.domain.response.ForecastResponse;
import ru.currencyforecast.lib.domain.response.Response;
import ru.currencyforecast.lib.domain.response.ResponseType;
import ru.currencyforecast.lib.repository.Repository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.currencyforecast.lib.common.Constant.*;

@Slf4j
@AllArgsConstructor
public class ServiceImpl implements Service {
    private final Repository repository;
    private final ForecastService forecastService;
    private final TrendService trendService;

    @Override
    public Response getForecast(Request request) {
        String output = request.getOutput();
        switch (output) {
            case OUTPUT_LIST:
                return getListForecast(request);
            case OUTPUT_GRAPH:
                return getTrendForecast(request);
            default:
                return prepareTextResponse(MESSAGE_WRONG_OUTPUT + output);
        }
    }

    public Response getListForecast(Request request) {
        String algoritm = request.getAlgorithm();
        if (forecastService.setAlgorithm(algoritm)) {
            List<CurrencyData> dataByAlgorithm = getDataByAlgorithm(request.getCurrencyList().get(0));
            List<CurrencyData> forecast = forecastService.getForecastForPeriod(dataByAlgorithm, request.getPeriodOrDate());
            return new ForecastResponse<>(ResponseType.DATA, new DataMessage(forecast));
        } else {
            return prepareTextResponse(MESSAGE_WRONG_ALG + algoritm);
        }
    }

    public Response getTrendForecast(Request request) {
        String algorithm = request.getAlgorithm();
        if (forecastService.setAlgorithm(algorithm)) {
            Map<String, List<CurrencyData>> currencyDataMap = getMultiCurrencyMap(request.getCurrencyList(), request.getPeriodOrDate());
            Optional<File> forecastTrend = trendService.getForecastTrend(currencyDataMap);
            return forecastTrend.map(this::prepareImageResponse).orElseGet(() -> prepareTextResponse(MESSAGE_WRONG_IMAGE));
        }
        return prepareTextResponse(MESSAGE_WRONG_ALG + algorithm);
    }

    private Response prepareTextResponse(String message) {
        return new ForecastResponse<>(ResponseType.TEXT, new TextMessage(message));
    }

    private List<CurrencyData> getDataByAlgorithm(String currency) {
        final int algBaseIndex = forecastService.getAlgBaseIndex();
        return repository.getDataByCdxAndLimitByALgBaseIndex(currency, algBaseIndex);
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

    private Response prepareImageResponse(File forecastTrend) {
        try {
            BufferedImage bufferedImage = ImageIO.read(forecastTrend);
            return new ForecastResponse<>(ResponseType.IMAGE, new ImageMessage(bufferedImage));
        } catch (IOException e) {
            log.info("ServiceImpl prepareImageResponse exception - {}", e.getMessage());
            e.printStackTrace();
        }
        return prepareTextResponse(MESSAGE_WRONG_IMAGE + forecastTrend.getPath());
    }
}


