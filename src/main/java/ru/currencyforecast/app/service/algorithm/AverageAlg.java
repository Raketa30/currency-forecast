package ru.currencyforecast.app.service.algorithm;

import ru.currencyforecast.app.domain.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AverageAlg implements ForecastAlgorithm {

    @Override
    public Data getTomorrowForecast(List<Data> dataList) {
        double average = getAverageCost(dataList);
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        return new Data(tomorrow, average);
    }

    private double getAverageCost(List<Data> dataList) {
        double average = 0.0;
        for (Data data : dataList) {
            average += data.getCost();
        }
        average = average / dataList.size();
        return average;
    }

    @Override
    public List<Data> getWeekForecast(List<Data> dataList) {
        List<Data> processList = new ArrayList<>(dataList);
        List<Data> weekDataList = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            int firstDay = i - 1;
            int lastDay = i + 6;
            double average = getAverageCost(processList.subList(firstDay, lastDay));

            LocalDate nextDay = LocalDate.now().plusDays(i);
            Data data = new Data(nextDay, average);
            processList.add(data);
            weekDataList.add(data);
        }

        return weekDataList;
    }
}
