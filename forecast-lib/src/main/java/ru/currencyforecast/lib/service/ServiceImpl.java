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
import ru.currencyforecast.lib.util.DateTimeUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

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
        String algoritm = request.getAlgorithm();
        if (forecastService.setAlgorithm(algoritm)) {
            switch (output) {
                case OUTPUT_LIST:
                    return getListForecast(request);
                case OUTPUT_GRAPH:
                    return getTrendForecast(request);
                default:
                    return prepareTextResponse(MESSAGE_WRONG_OUTPUT + output);
            }
        } else {
            return prepareTextResponse(MESSAGE_WRONG_ALG + algoritm);
        }
    }

    public Response getListForecast(Request request) {
        List<CurrencyData> dataByAlgorithm = getDataListByAlgorithmIndex(request.getCurrencyList().get(0));
        List<CurrencyData> forecast = getCurrencyDataListByPeriodOrDate(request, dataByAlgorithm);
        return new ForecastResponse<>(ResponseType.DATA, new DataMessage(forecast));
    }

    public Response getTrendForecast(Request request) {
        Map<String, List<CurrencyData>> currencyDataMap = getMultiCurrencyMap(request.getCurrencyList(), request.getPeriodOrDate());
        Optional<File> forecastTrend = trendService.getForecastTrend(currencyDataMap);
        return forecastTrend.map(this::prepareImageResponse).orElseGet(() -> prepareTextResponse(MESSAGE_WRONG_IMAGE));
    }

    private Response prepareTextResponse(String message) {
        return new ForecastResponse<>(ResponseType.TEXT, new TextMessage(message));
    }

    private List<CurrencyData> getDataListByAlgorithmIndex(String currency) {
        final int algBaseIndex = forecastService.getAlgBaseIndex();
        return repository.getDataByCdxAndLimitByALgBaseIndex(currency, algBaseIndex);
    }

    private List<CurrencyData> getCurrencyDataListByPeriodOrDate(Request request, List<CurrencyData> dataByAlgorithm) {
        String periodOrDate = request.getPeriodOrDate();
        if (DateTimeUtil.isDate(periodOrDate)) {
            Optional<CurrencyData> forecastForDate = forecastService.getForecastForDate(dataByAlgorithm, DateTimeUtil.getLocalDate(periodOrDate));
            return forecastForDate.map(Collections::singletonList).orElse(Collections.emptyList());
        }
        return forecastService.getForecastForPeriod(dataByAlgorithm, periodOrDate);
    }

    private Map<String, List<CurrencyData>> getMultiCurrencyMap(List<String> currency, String period) {
        Map<String, List<CurrencyData>> currencyDataMap = new HashMap<>();
        for (String cur : currency) {
            List<CurrencyData> dataListByAlgorithm = getDataListByAlgorithmIndex(cur);
            List<CurrencyData> processedDataList = forecastService.getForecastForPeriod(dataListByAlgorithm, period);
            currencyDataMap.put(cur, processedDataList);
        }
        return currencyDataMap;
    }

    private Response prepareImageResponse(File forecastTrend) {
        try {
            BufferedImage bufferedImage = ImageIO.read(forecastTrend);
            return new ForecastResponse<>(ResponseType.IMAGE, new ImageMessage(bufferedImage));
        } catch (IOException e) {
            log.info("ServiceImpl prepareImageResponse exception - {}", e.getMessage());
        }
        return prepareTextResponse(MESSAGE_WRONG_IMAGE + forecastTrend.getPath());
    }
}


