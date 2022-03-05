package ru.currencyforecast.app.service.algorithm;

import ru.currencyforecast.app.domain.CurrencyData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AverageAlgorithm implements AlgorithmService {
    @Override
    public List<CurrencyData> getForecast(List<CurrencyData> currencyDataList, int days) {
        List<CurrencyData> processList = new ArrayList<>(currencyDataList);
        List<CurrencyData> weekCurrencyDataList = new ArrayList<>();
        String titleFormList = getDataTitleFormList(currencyDataList);
        int nextDayCounter = 1;
        for (int i = 0; i < days; i++) {

            double average = getAverageCost(processList.subList(i, processList.size()));
            LocalDate nextDay = LocalDate.now().plusDays(nextDayCounter++);
            CurrencyData currencyData = new CurrencyData(100, nextDay, average, titleFormList);
            processList.add(currencyData);
            weekCurrencyDataList.add(currencyData);
        }
        return weekCurrencyDataList;
    }

    private String getDataTitleFormList(List<CurrencyData> currencyDataList) {
        return currencyDataList.get(0).getCdx();
    }

    private double getAverageCost(List<CurrencyData> currencyDataList) {
        return currencyDataList.stream()
                .mapToDouble(CurrencyData::getCurs)
                .average().getAsDouble();
    }
}
