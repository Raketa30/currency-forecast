package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.ArrayList;
import java.util.List;

import static ru.currencyforecast.lib.common.Constant.ALG_ACTUAL_BASE;

/**
 * �������� �����������.
 * ��������������, ��� ����� ����� �� (������� ��� - 2 + ������� ��� - 3),
 * �� ���� ������� �� 25.12.2022 ����� ��������� ��� ������� 25.12.2020 + 25.12.2019.
 * ���� ����� ������ ������� � ��� ������ �� ��� - ������ ������.
 */
public class ActualAlgorithmImpl implements Algorithm {
    @Override
    public List<CurrencyData> getForcastForPeriod(List<CurrencyData> dataListForAnalisys, int periodDays) {
        List<CurrencyData> processList = new ArrayList<>(dataListForAnalisys);
        List<CurrencyData> resultList = new ArrayList<>();
        String titleFormList = getDataTitleFormList(dataListForAnalisys);
        int currencyNominal = getCurrencyNominal(dataListForAnalisys);


        return resultList;

    }

    private String getDataTitleFormList(List<CurrencyData> dataFromRepository) {
        return dataFromRepository.get(0).getCdx();
    }

    private int getCurrencyNominal(List<CurrencyData> dataFromRepository) {
        return dataFromRepository.get(0).getNominal();
    }

    @Override
    public int getBaseDaysNumber() {
        return ALG_ACTUAL_BASE;
    }
}
