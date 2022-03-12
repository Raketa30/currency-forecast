package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;

/**
 * ������ ���������
 */
public interface ForecastService {
    /**
     * @return ������� ������ ���������
     */
    int getAlgBaseIndex();

    /**
     * ��������� �������� ��� ������������ ����
     *
     * @param incomingDataList - �������� ������ ������ ��� �������
     * @param date             - ���������� ����
     * @return ������ ����������������� ������
     */
    List<CurrencyData> getForecastForDate(List<CurrencyData> incomingDataList, String date);

    /**
     * @param incomingDataList - �������� ������ ������ ��� �������
     * @param period           - �������� ������ ��������
     * @return ������ ����������������� ������
     */
    List<CurrencyData> getForecastForPeriod(List<CurrencyData> incomingDataList, String period);

    /**
     * ��������� ���������� ��������� ��������
     *
     * @param algorithm
     * @return false ���� ����������� ���������� ��������
     */
    boolean setAlgorithm(String algorithm);
}
