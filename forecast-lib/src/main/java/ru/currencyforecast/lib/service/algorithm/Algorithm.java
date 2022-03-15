package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;

/**
 * Алгоритм прогноза
 */
public abstract class Algorithm {
    /**
     * количество предыдущих записей, требующихся для прогноза
     */
    private final int baseDaysIndex;

    protected Algorithm(int baseDaysIndex) {
        this.baseDaysIndex = baseDaysIndex;
    }

    public int getBaseDaysNumber() {
        return baseDaysIndex;
    }

    /**
     * Прогноз для указанного периода в днях
     *
     * @param dataListForAnalisys - входящий список данных
     * @param periodDays          - прогнозируемый период
     * @return список с прогнозом на количсетво дней
     */
    public abstract List<CurrencyData> getForcastForPeriod(List<CurrencyData> dataListForAnalisys, int periodDays);

    protected String getDataTitleFormList(List<CurrencyData> currencyDataList) {
        return currencyDataList.get(0).getCdx();
    }

    protected int getCurrencyNominal(List<CurrencyData> currencyDataList) {
        return currencyDataList.get(0).getNominal();
    }
}


