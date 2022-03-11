package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ������ ��������� ������ � �������� �������
 */
public interface TrendService {
    /**
     * ��������� ������ �� ������ �������� �����
     *
     * @param forecast - ����� ����� � �� ������
     * @return ������������ ���� � ������������
     */
    Optional<File> getForecastTrend(Map<String, List<CurrencyData>> forecast);
}
