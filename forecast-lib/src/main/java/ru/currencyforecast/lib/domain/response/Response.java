package ru.currencyforecast.lib.domain.response;

import ru.currencyforecast.lib.domain.message.Message;

/**
 * ��������� �������������� �����, ���������� ����������� ��������� � ���� ���������� ��������
 * ��������� ��������� � ������� ����������
 */
public interface Response {
    Message getMessage();

    boolean isPicture();
}
