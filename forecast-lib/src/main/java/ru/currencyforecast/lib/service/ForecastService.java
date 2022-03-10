package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.service.algorithm.Algorithm;

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
     * @param incomingDataList - �������� ������ ������ ��� �������
     * @param date - ���������� ����
     * @return ������ ����������������� ������
     */
    List<CurrencyData> getForecastForDate(List<CurrencyData> incomingDataList, String date);

    /**
     * @param incomingDataList - �������� ������ ������ ��� �������
     * @param period - �������� ������ ��������
     * @return ������ ����������������� ������
     */
    List<CurrencyData> getForecastForPeriod(List<CurrencyData> incomingDataList, String period);

    /**
     * ��������� ���������� ��������� ��������
     * @param algorithm
     */
    void setAlgorithm(Algorithm algorithm);
}
