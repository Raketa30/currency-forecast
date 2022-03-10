package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.response.Response;

import java.util.List;

/**
 * �������� ������ ���������� ?? - ���������� � ���� ����� ������������  ������� � �������������� ����������
 */
public interface Service {
    /**
     * ����� ������������ ����� ��������� � ���� ������
     *
     * @param currency  - ������
     * @param period    - ��������� ������
     * @param algroithm - ��������
     * @return Response
     */
    Response getListForecast(String currency, String period, String algroithm);

    /**
     * ����� ������������ ����� ��������� � ���� �������
     *
     * @param currencyList - ������
     * @param period       - ��������� ������
     * @param algroithm    - ��������
     * @return Response
     */
    Response getTrendForecast(List<String> currencyList, String period, String algroithm);
}
