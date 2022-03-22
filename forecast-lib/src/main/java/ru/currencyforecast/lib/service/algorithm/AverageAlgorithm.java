package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static ru.currencyforecast.lib.common.Constant.ALG_AVG_BASE;

/**
 * Алгоритм по среднему значению за период
 */
public class AverageAlgorithm extends Algorithm {

    public AverageAlgorithm() {
        super(ALG_AVG_BASE);
    }


    @Override
    protected void doForecast(String cdx, int nominal, LocalDate nextDay, LinkedList<CurrencyData> processList, List<CurrencyData> resultList, int periodDays) {
        for (int i = 0; i < periodDays; i++) {
            nextDay = nextDay.plusDays(1);
            double cost = getAverageCost(processList.subList(0, Math.min(processList.size(), ALG_AVG_BASE)));
            CurrencyData currencyData = new CurrencyData(nominal, nextDay, cost, cdx);
            processList.addFirst(currencyData);
            resultList.add(currencyData);
        }
    }

    private double getAverageCost(List<CurrencyData> currencyDataList) {
        return currencyDataList.stream()
                .mapToDouble(CurrencyData::getCurs)
                .average()
                .getAsDouble();
    }
}
