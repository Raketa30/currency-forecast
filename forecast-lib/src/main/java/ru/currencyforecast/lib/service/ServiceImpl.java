package ru.currencyforecast.lib.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.domain.ForecastRequest;
import ru.currencyforecast.lib.domain.message.DataMessage;
import ru.currencyforecast.lib.domain.message.ImageMessage;
import ru.currencyforecast.lib.domain.message.TextMessage;
import ru.currencyforecast.lib.domain.response.Response;
import ru.currencyforecast.lib.domain.response.ResponseImpl;
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
    public Response getForecastResponse(ForecastRequest request) {
        String output = request.getOutput();
        String algoritm = request.getAlgorithm();
        if (forecastService.setAlgorithm(algoritm)) {
            switch (output) {
                case OUTPUT_LIST:
                    return getDataResponse(request);
                case OUTPUT_GRAPH:
                    return getImageResponse(request);
                default:
                    return getTextResponse(MESSAGE_WRONG_OUTPUT + output);
            }
        } else {
            log.debug("ServiceImpl getForecast, request  - {}", request);
            return getTextResponse(MESSAGE_WRONG_ALG + algoritm);
        }
    }

    /**
     * @param request - ������ ��������
     * @return Response � ���������������� �������
     */
    private Response getDataResponse(ForecastRequest request) {
        List<CurrencyData> dataByAlgorithm = getCurrencyDataList(request.getCurrencyList().get(0));
        if (dataByAlgorithm.isEmpty()) {
            return getTextResponse(WRONG_CURRENCY_OR_EMPTY_DATA_IN_CSV_FILE);
        }
        List<CurrencyData> forecast = getDataListByPeriodOrDate(request, dataByAlgorithm);
        return new ResponseImpl<>(ResponseType.DATA, new DataMessage(forecast));
    }

    /**
     * @param request - ������ ��������
     * @return Response � ��������� ����������
     */
    private Response getImageResponse(ForecastRequest request) {
        Map<String, List<CurrencyData>> currencyDataMap = getMultiCurrencyForecastMap(request.getCurrencyList(), request.getPeriodOrDate());
        Optional<File> forecastTrend = trendService.getForecastTrend(currencyDataMap);
        return forecastTrend.map(this::getImageResponse).orElseGet(() -> getTextResponse(MESSAGE_WRONG_IMAGE));
    }

    /**
     * @param message - ���������
     * @return Response � ��������� ����������
     */
    private Response getTextResponse(String message) {
        return new ResponseImpl<>(ResponseType.TEXT, new TextMessage(message));
    }

    /**
     * @param currency - ������ ��������
     * @return ������ ��������� ������ � ���������� ��������� ������ ��������� ����������(algBaseIndex)
     */
    private List<CurrencyData> getCurrencyDataList(String currency) {
        final int algBaseIndexDays = forecastService.getAlgBaseIndex();
        log.debug("ServiceImpl getDataListByAlgorithmIndex, algBaseIndexDays  - {}", algBaseIndexDays);
        return repository.getDataByCdxAndLimitByALgBaseIndex(currency, algBaseIndexDays);
    }

    /**
     * @param request          - ������ ��������
     * @param dataListFromRepo - ���� ����� � �����������
     * @return ���������������� ������ �� ��������� ���� ��� ������
     */
    private List<CurrencyData> getDataListByPeriodOrDate(ForecastRequest request, List<CurrencyData> dataListFromRepo) {
        String periodOrDate = request.getPeriodOrDate();
        if (DateTimeUtil.isDate(periodOrDate)) {
            Optional<CurrencyData> forecastForDate = forecastService.getForecastForDate(dataListFromRepo, DateTimeUtil.getLocalDate(periodOrDate));
            return forecastForDate.map(Collections::singletonList).orElse(Collections.emptyList());
        }
        log.debug("ServiceImpl getCurrencyDataListByPeriodOrDate, period or date  - {}", periodOrDate);
        return forecastService.getForecastForPeriod(dataListFromRepo, periodOrDate);
    }

    /**
     * @param currencyList - ������ �����
     * @param period       - ������ ��������
     * @return ����� ����������������� �����
     */
    private Map<String, List<CurrencyData>> getMultiCurrencyForecastMap(List<String> currencyList, String period) {
        Map<String, List<CurrencyData>> currencyDataMap = new HashMap<>();
        for (String cur : currencyList) {
            List<CurrencyData> dataListByAlgorithm = getCurrencyDataList(cur);
            List<CurrencyData> processedDataList = forecastService.getForecastForPeriod(dataListByAlgorithm, period);
            currencyDataMap.put(cur, processedDataList);
        }
        log.debug("ServiceImpl getMulticurrencyMap, mapSize  - {}", currencyDataMap.size());
        return currencyDataMap;
    }

    /**
     * @param imageFile -���� � ������������ �������
     * @return Response � Buffer�dImage, ����������� ������������ ��������
     */
    private Response getImageResponse(File imageFile) {
        try {
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            return new ResponseImpl<>(ResponseType.IMAGE, new ImageMessage(bufferedImage));
        } catch (IOException e) {
            log.info("ServiceImpl prepareImageResponse exception - {}", e.getMessage());
        }
        return getTextResponse(MESSAGE_WRONG_IMAGE + imageFile.getPath());
    }
}


