package ru.currencyforecast.app.service.algorithm;

import ru.currencyforecast.app.domain.CurrencyData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AverageAlgorithm implements AlgorithmService {

    @Override
    public CurrencyData getTomorrowForecast(List<CurrencyData> currencyDataList) {
        String titleFormList = getDataTitleFormList(currencyDataList);
        double average = getAverageCost(currencyDataList);
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        return new CurrencyData(tomorrow, average, titleFormList);
    }

    @Override
    public List<CurrencyData> getWeekForecast(List<CurrencyData> currencyDataList) {
        List<CurrencyData> processList = new ArrayList<>(currencyDataList);
        List<CurrencyData> weekCurrencyDataList = new ArrayList<>();
        String titleFormList = getDataTitleFormList(currencyDataList);
        int nextDayCounter = 1;
        for (int i = 0; i < currencyDataList.size(); i++) {
            double average = getAverageCost(processList.subList(i, processList.size()));
            LocalDate nextDay = LocalDate.now().plusDays(nextDayCounter++);
            CurrencyData currencyData = new CurrencyData(nextDay, average, titleFormList);
            processList.add(currencyData);
            weekCurrencyDataList.add(currencyData);
        }
        return weekCurrencyDataList;
    }

    private String getDataTitleFormList(List<CurrencyData> currencyDataList) {
        return currencyDataList.get(0).getTitle();
    }

    private double getAverageCost(List<CurrencyData> currencyDataList) {
        return currencyDataList.stream()
                .mapToDouble(CurrencyData::getCost)
                .average().getAsDouble();
    }
}
