package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;

/**
 * �������� ��������
 */
public interface Algorithm {
    /**
     * @return ���������� ���������� �������, ����������� ��� ��������
     */
    int getBaseDaysNumber();

    /**
     * ������� ��� ���������� ������� � ����
     *
     * @param dataListForAnalisys - �������� ������ ������
     * @param periodDays          - ���������� ������������ ����
     * @return ������ � ��������� �� ���������� ����
     */
    List<CurrencyData> getForcastForPeriod(List<CurrencyData> dataListForAnalisys, int periodDays);
}
