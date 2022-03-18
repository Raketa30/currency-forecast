package ru.currencyforecast.lib.controller;

import ru.currencyforecast.lib.domain.ForecastRequest;

/**
 * �������� ����������
 */
public interface Controller {
    /**
     * �������� ��������� � Response
     *
     * @param message - ����� ���������
     */
    void addMessage(String message);

    /**
     * ���������������� ���������� �������� � �����������:
     *
     * @param request - ��������� �������
     */
    void execute(ForecastRequest request);
}
