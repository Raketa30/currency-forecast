package ru.currencyforecast.lib.controller;

import ru.currencyforecast.lib.domain.Request;

/**
 * �������� ����������
 */
public interface Controller {
    /**
     * �������� ��������� � Response
     * @param message - ����� ���������
     */
    void addMessage(String message);

    /**
     * ���������������� ���������� �������� � �����������:
        @param request - ��������� �������
     */
    void execute(Request request);
}
