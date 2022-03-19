package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
    private final Random random;

    public MisticAlgorithm() {
        super(ALG_MISTIC_BASE);
        this.random = new Random();
    }

    @Override
    public List<CurrencyData> getForcastForPeriod(List<CurrencyData> dataListForAnalisys, int periodDays) {
        LinkedList<CurrencyData> processList = new LinkedList<>(dataListForAnalisys);
        List<CurrencyData> resultList = new ArrayList<>();
        String titleFormList = getDataTitleFormList(dataListForAnalisys);
        int currencyNominal = getCurrencyNominal(dataListForAnalisys);
        LocalDate nextDay = LocalDate.now().plusDays(1);
        double price = getAverageTreeFullmoonPrice(processList);
        CurrencyData currencyData = new CurrencyData(currencyNominal, nextDay, price, titleFormList);
        processList.addFirst(currencyData);
        resultList.add(currencyData);

        if (periodDays > FORECAST_DEPTH_TOMORROW_INDEX) {
            addNextDataToResult(processList, resultList, periodDays - 1);
        }

        return resultList;
    }

    private int addNextDataToResult(LinkedList<CurrencyData> processList, List<CurrencyData> resultList, int daysLeft) {
        if (daysLeft == 0) {
            return 0;
        }
        int coin = random.nextInt(2);
        double curs = processList.get(0).getCurs();

        if (coin == 0) {
            curs = curs - curs * 0.1;
        } else {
            curs = curs + curs * 0.1;
        }
        String titleFormList = getDataTitleFormList(processList);
        int currencyNominal = getCurrencyNominal(processList);
        LocalDate nextDay = processList.get(0).getData().plusDays(1);

        CurrencyData currencyData = new CurrencyData(currencyNominal, nextDay, curs, titleFormList);
        processList.addFirst(currencyData);
        resultList.add(currencyData);

        return addNextDataToResult(processList, resultList, --daysLeft);
    }

    private double getAverageTreeFullmoonPrice(List<CurrencyData> processList) {
        return processList.stream()
                .filter(currencyData -> DateTimeUtil.isFullMoon(currencyData.getData()))
                .mapToDouble(CurrencyData::getCurs).limit(FULLMOON_LIMIT)
                .average()
                .getAsDouble();
    }

}
