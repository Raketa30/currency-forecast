package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.currencyforecast.lib.common.Constant.ALG_AVG_BASE;

/**
 * Алгоритм по среднему значению за период
 */
public class AverageAlgorithmImpl implements Algorithm {
    @Override
    public List<CurrencyData> getForcastForPeriod(List<CurrencyData> dataListForAnalisys, int periodDays) {
        List<CurrencyData> processList = new ArrayList<>(dataListForAnalisys);
        List<CurrencyData> weekCurrencyDataList = new ArrayList<>();
        String titleFormList = getDataTitleFormList(dataListForAnalisys);
        int currencyNominal = getCurrencyNominal(dataListForAnalisys);
        int nextDayCounter = 1;
        for (int i = 0; i < periodDays; i++) {
            double average = getAverageCost(processList.subList(i, processList.size()));
            LocalDate nextDay = LocalDate.now().plusDays(nextDayCounter++);
            CurrencyData currencyData = new CurrencyData(currencyNominal, nextDay, average, titleFormList);
            processList.add(currencyData);
            weekCurrencyDataList.add(currencyData);
        }
        return weekCurrencyDataList;
    }

    @Override
    public int getBaseDaysNumber() {
        return ALG_AVG_BASE;
    }

    private String getDataTitleFormList(List<CurrencyData> currencyDataList) {
        return currencyDataList.get(0).getCdx();
    }

    private int getCurrencyNominal(List<CurrencyData> currencyDataList) {
        return currencyDataList.get(0).getNominal();
    }

    private double getAverageCost(List<CurrencyData> currencyDataList) {
        return currencyDataList.stream()
                .mapToDouble(CurrencyData::getCurs)
                .average()
                .getAsDouble();
    }
}
