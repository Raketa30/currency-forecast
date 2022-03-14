package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;

import static ru.currencyforecast.lib.common.Constant.ALG_MISTIC_BASE;

/**
 * �������� ������������
 * - ��� ������� �� ���� ���������� ������� �������������� �� ���� ��������� �� ���� ���� ����������.
 * - ��� ������� �� ������ � �����. ������ ���� �������������� ���������� ����������� ������.
 * ����������� ���� �������������� ����������� �� ������� -
 * �������� ���������� ����  + ��������� ����� �� -10% �� +10% �� �������� ���������� ����.
 */
public class MisticAlgorithm extends Algorithm {

    public MisticAlgorithm() {
        super(ALG_MISTIC_BASE);
    }

    @Override
    public List<CurrencyData> getForcastForPeriod(List<CurrencyData> dataListForAnalisys, int periodDays) {
        return null;
    }
}
