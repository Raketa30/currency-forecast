package ru.currencyforecast.lib.cli;

/**
 * ���������� ��������� ������
 */
public interface Commander {
    /**
     * @param command - �������� ������������� �������
     */
    boolean execute(String command);
}
