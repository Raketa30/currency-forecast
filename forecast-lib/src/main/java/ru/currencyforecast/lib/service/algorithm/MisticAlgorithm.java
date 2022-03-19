package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static ru.currencyforecast.lib.common.Constant.ALG_MISTIC_BASE;
import static ru.currencyforecast.lib.common.Constant.FORECAST_DEPTH_TOMORROW_INDEX;

/**
 * Алгоритм “Мистический”
 * - Для расчета на дату используем среднее арифметическое из трех последних от этой даты полнолуний.
 * - Для расчета на неделю и месяц. Первый курс рассчитывается аналогично предыдущему пункту.
 * Последующие даты рассчитываются рекуррентно по формуле -
 * значение предыдущей даты  + случайное число от -10% до +10% от значения предыдущей даты.
 */
public class MisticAlgorithm extends Algorithm {
    private static final int FULLMOON_LIMIT = 3;
    private static final double RANGE_MAX = 0.1;
    private static final double RANGE_MIN = -0.1;
    public MisticAlgorithm() {
        super(ALG_MISTIC_BASE);
    }

    @Override
    protected void doForecast(String cdx, int nominal, LocalDate nextDay, LinkedList<CurrencyData> processList, List<CurrencyData> resultList, int periodDays) {
        nextDay = nextDay.plusDays(1);
        double price = getAverageTreeFullmoonPrice(processList);
        CurrencyData currencyData = new CurrencyData(nominal, nextDay, price, cdx);
        processList.addFirst(currencyData);
        resultList.add(currencyData);

        if (periodDays > FORECAST_DEPTH_TOMORROW_INDEX) {
            addNextDataToResult(cdx, nominal, nextDay, processList, resultList, periodDays - 1);
        }
    }

    private int addNextDataToResult(String titleFormList, int currencyNominal, LocalDate nextDay, LinkedList<CurrencyData> processList, List<CurrencyData> resultList, int daysLeft) {
        if (daysLeft == 0) {
            return 0;
        }
        double curs = processList.get(0).getCurs();
        double deviation = ThreadLocalRandom.current().nextDouble(RANGE_MIN, RANGE_MAX);
        curs = curs + curs * deviation;
        nextDay = nextDay.plusDays(1);
        CurrencyData currencyData = new CurrencyData(currencyNominal, nextDay, curs, titleFormList);
        processList.addFirst(currencyData);
        resultList.add(currencyData);

        return addNextDataToResult(titleFormList, currencyNominal, nextDay, processList, resultList, --daysLeft);
    }

    private double getAverageTreeFullmoonPrice(List<CurrencyData> processList) {
        return processList.stream()
                .filter(currencyData -> DateTimeUtil.isFullMoon(currencyData.getData()))
                .mapToDouble(CurrencyData::getCurs).limit(FULLMOON_LIMIT)
                .average()
                .getAsDouble();
    }


}
