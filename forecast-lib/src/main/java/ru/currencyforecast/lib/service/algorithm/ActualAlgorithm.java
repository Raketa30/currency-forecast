package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static ru.currencyforecast.lib.common.Constant.ALG_ACTUAL_BASE;

/**
 * Алгоритм “Актуальный”.
 * Рассчитывается, как сумма курса за (текущий год - 2 + текущий год - 3),
 * то есть прогноз на 25.12.2022 будет считаться как прогноз 25.12.2020 + 25.12.2019.
 * Если число сильно впереди и нет данных за год - кидать ошибку.
 */
public class ActualAlgorithm extends Algorithm {

    public ActualAlgorithm() {
        super(ALG_ACTUAL_BASE);
    }

    @Override
    protected void doForecast(String cdx, int nominal, LocalDate nextDay, LinkedList<CurrencyData> processList, List<CurrencyData> resultList, int periodDays) {
        for (int i = 0; i < periodDays; i++) {
            nextDay = nextDay.plusDays(1);
            double cost = getNextDayPrice(processList, nextDay);
            CurrencyData currencyData = new CurrencyData(nominal, nextDay, cost, cdx);
            processList.addFirst(currencyData);
            resultList.add(currencyData);
        }
    }

    private double getNextDayPrice(List<CurrencyData> processList, LocalDate nextDay) {
        LocalDate dateTwoYearsBefore = nextDay.minusYears(2L);
        LocalDate dateThreeYearsBefore = nextDay.minusYears(3L);
        double twoYearsBeforePrice = getPriceByDate(processList, dateTwoYearsBefore);
        double threeYearsBeforePrice = getPriceByDate(processList, dateThreeYearsBefore);
        return twoYearsBeforePrice + threeYearsBeforePrice;
    }

    private double getPriceByDate(List<CurrencyData> currencyDataList, LocalDate localDate) {
        double temp = 0;
        for (CurrencyData data : currencyDataList) {
            if (data.getData().isEqual(localDate)) {
                return data.getCurs();
            } else if (data.getData().isBefore(localDate)) {
                return temp;
            }
            temp = data.getCurs();
        }
        return temp;
    }
}
