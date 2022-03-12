package ru.currencyforecast.lib.domain.response;

import ru.currencyforecast.lib.domain.message.AbstractMessage;

/**
 * ��������� �������������� �����, ���������� ����������� ��������� � ��� ������
 */
public interface Response {
    <T> AbstractMessage<T> getMessage();

    ResponseType getType();
}
