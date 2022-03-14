package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;

import static ru.currencyforecast.lib.common.Constant.ALG_MISTIC_BASE;

/**
 * Алгоритм “Мистический”
 * - Для расчета на дату используем среднее арифметическое из трех последних от этой даты полнолуний.
 * - Для расчета на неделю и месяц. Первый курс рассчитывается аналогично предыдущему пункту.
 * Последующие даты рассчитываются рекуррентно по формуле -
 * значение предыдущей даты  + случайное число от -10% до +10% от значения предыдущей даты.
 */
public class MisticAlgorithm extends Algorithm {

    public MisticAlgorithm() {
        super(ALG_MISTIC_BASE);
    }

    @Override
    public List<CurrencyData> getForcastForPeriod(List<CurrencyData> dataListForAnalisys, int periodDays) {
        return null;
    }
}
