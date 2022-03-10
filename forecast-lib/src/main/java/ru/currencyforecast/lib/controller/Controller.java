package ru.currencyforecast.lib.controller;

import java.util.List;

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
     * @param currency - ������ �����
     * @param period - ������ ��� ���������� ����
     * @param algorithm - �������� ��������
     * @param output - �����: ���� ��� ������
     */
    void execute(List<String> currency, String period, String algorithm, String output);
}
