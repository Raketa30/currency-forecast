package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
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
    public List<CurrencyData> getForcastForPeriod(List<CurrencyData> dataListForAnalisys, int periodDays) {
        LinkedList<CurrencyData> processList = new LinkedList<>(dataListForAnalisys);
        List<CurrencyData> resultList = new ArrayList<>();
        String titleFormList = getDataTitleFormList(dataListForAnalisys);
        int currencyNominal = getCurrencyNominal(dataListForAnalisys);
        LocalDate nextDay = LocalDate.now();
        doForecast(titleFormList, currencyNominal, nextDay, processList, resultList, periodDays);
        return resultList;
    }

    private String getDataTitleFormList(List<CurrencyData> currencyDataList) {
        return currencyDataList.get(0).getCdx();
    }

    private int getCurrencyNominal(List<CurrencyData> currencyDataList) {
        return currencyDataList.get(0).getNominal();
    }

    protected abstract void doForecast(String cdx, int nominal, LocalDate nextDay, LinkedList<CurrencyData> processList, List<CurrencyData> resultList, int periodDays);
}


