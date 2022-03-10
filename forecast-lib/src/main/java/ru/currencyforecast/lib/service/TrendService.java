package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * ������ ��������� ������ � �������� �������
 */
public interface TrendService {
    /**
     * ��������� ������ �� ������ �������� �����
     *
     * @param forecast - ����� ����� � �� ������
     * @return ������ �� ����������� �����������
     */
    Path getForecastTrend(Map<String, List<CurrencyData>> forecast);
}
