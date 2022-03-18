package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.ForecastRequest;
import ru.currencyforecast.lib.domain.response.Response;

/**
 * �������� ������ ����������
 */
public interface Service {
    /**
     * ����� ������������ ����� ��������� � ���� �������
     *
     * @param request - ������ ��������
     * @return Response
     */
    Response getForecastResponse(ForecastRequest request);
}
