package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
   Optional<CurrencyData> getForecastForDate(List<CurrencyData> incomingDataList, LocalDate date);

    /**
     * @param incomingDataList - �������� ������ ������ ��� �������
     * @param period           - �������� ������ ��������
     * @return ������ ����������������� ������
     */
    List<CurrencyData> getForecastForPeriod(List<CurrencyData> incomingDataList, String period);

    /**
     * ��������� ���������� ��������� ��������
     *
     * @param algorithm - �������� ��������
     * @return false ���� ����������� ���������� ��������
     */
    boolean setAlgorithm(String algorithm);
}
