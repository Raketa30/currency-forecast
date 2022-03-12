package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;

import static ru.currencyforecast.lib.common.Constant.ALG_INTERNET_BASE;

/**
 * јлгоритм Уиз интернетаФ. Ёкстраполируем по данным последнего мес€ца.
 * <a href="https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/LinearRegression.java.html">ссылка на алгоритм</a>
 */
public class InternetAlgorithmImpl implements Algorithm {
    @Override
    public int getBaseDaysNumber() {
        return ALG_INTERNET_BASE;
    }

    @Override
    public List<CurrencyData> getForcastForPeriod(List<CurrencyData> dataListForAnalisys, int periodDays) {
        return null;
    }
}
