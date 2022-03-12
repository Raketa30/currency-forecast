package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.Request;
import ru.currencyforecast.lib.domain.response.Response;

/**
 * �������� ������ ���������� ?? - ���������� � ���� ����� ������������  ������� � �������������� ����������
 */
public interface Service {
    /**
     * ����� ������������ ����� ��������� � ���� �������
     *
     * @param request - ������ ��������
     * @return Response
     */
    Response getForecast(Request request);
}
