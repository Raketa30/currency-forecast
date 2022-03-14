package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;

/**
 * �������� ��������
 */
public abstract class Algorithm {
    /**
     * ���������� ���������� �������, ����������� ��� ��������
     */
    private final int baseDaysIndex;

    protected Algorithm(int baseDaysIndex) {
        this.baseDaysIndex = baseDaysIndex;
    }

    public int getBaseDaysNumber() {
        return baseDaysIndex;
    }

    /**
     * ������� ��� ���������� ������� � ����
     *
     * @param dataListForAnalisys - �������� ������ ������
     * @param periodDays          - �������������� ������
     * @return ������ � ��������� �� ���������� ����
     */
    public abstract List<CurrencyData> getForcastForPeriod(List<CurrencyData> dataListForAnalisys, int periodDays);
}


