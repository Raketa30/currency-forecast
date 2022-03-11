package ru.currencyforecast.lib.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.domain.message.ImageMessageImpl;
import ru.currencyforecast.lib.domain.message.TextMessageImpl;
import ru.currencyforecast.lib.domain.response.Response;
import ru.currencyforecast.lib.domain.response.ResponseImpl;
import ru.currencyforecast.lib.repository.Repository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.currencyforecast.lib.common.Constant.MESSAGE_WRONG_ALG;
import static ru.currencyforecast.lib.common.Constant.MESSAGE_WRONG_IMAGE;

@Slf4j
@AllArgsConstructor
public class ServiceImpl implements Service {
    private final Repository repository;
    private final ForecastService forecastService;
    private final TrendService trendService;

    @Override
    public Response getListForecast(String currency, String period, String algoritm) {
        if (forecastService.setAlgorithm(algoritm)) {
            List<CurrencyData> dataByAlgorithm = getDataByAlgorithm(currency);
            List<CurrencyData> forecast = forecastService.getForecastForPeriod(dataByAlgorithm, period);
            return prepareTextResponse(transformToText(forecast));
        } else {
            return prepareTextResponse(MESSAGE_WRONG_ALG + algoritm);
        }
    }

    @Override
    public Response getTrendForecast(List<String> currencyList, String period, String algorithm) {
        if (forecastService.setAlgorithm(algorithm)) {
            Map<String, List<CurrencyData>> currencyDataMap = getMultiCurrencyMap(currencyList, period);
            Optional<File> forecastTrend = trendService.getForecastTrend(currencyDataMap);
            return forecastTrend.map(this::prepareImageResponse).orElseGet(() -> prepareTextResponse(MESSAGE_WRONG_IMAGE));
        }
            return prepareTextResponse(MESSAGE_WRONG_ALG + algorithm);
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
            return new ResponseImpl(new ImageMessageImpl(bufferedImage), true);
        } catch (IOException e) {
            log.info("ServiceImpl prepareImageResponse exception - {}", e.getMessage());
            e.printStackTrace();
        }
        return prepareTextResponse(MESSAGE_WRONG_IMAGE + forecastTrend.getPath());
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


