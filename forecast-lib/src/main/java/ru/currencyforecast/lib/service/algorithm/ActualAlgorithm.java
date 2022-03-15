package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static ru.currencyforecast.lib.common.Constant.ALG_ACTUAL_BASE;

/**
 * �������� �����������.
 * ��������������, ��� ����� ����� �� (������� ��� - 2 + ������� ��� - 3),
 * �� ���� ������� �� 25.12.2022 ����� ��������� ��� ������� 25.12.2020 + 25.12.2019.
 * ���� ����� ������ ������� � ��� ������ �� ��� - ������ ������.
 */
public class ActualAlgorithm extends Algorithm {

    public ActualAlgorithm() {
        super(ALG_ACTUAL_BASE);
    }

    @Override
    public List<CurrencyData> getForcastForPeriod(List<CurrencyData> dataListForAnalisys, int periodDays) {
        LinkedList<CurrencyData> processList = new LinkedList<>(dataListForAnalisys);
        List<CurrencyData> resultList = new ArrayList<>();
        String titleFormList = getDataTitleFormList(dataListForAnalisys);
        int currencyNominal = getCurrencyNominal(dataListForAnalisys);
        LocalDate nextDay = LocalDate.now();
        for (int i = 0; i < periodDays; i++) {
            nextDay = nextDay.plusDays(1);
            double cost = getNextDayPrice(processList, nextDay);
            CurrencyData currencyData = new CurrencyData(currencyNominal, nextDay, cost, titleFormList);
            processList.addFirst(currencyData);
            resultList.add(currencyData);
        }
        return resultList;
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
