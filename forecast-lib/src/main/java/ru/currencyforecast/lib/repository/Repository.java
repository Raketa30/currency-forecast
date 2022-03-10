package ru.currencyforecast.lib.repository;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;
import java.util.Optional;

/**
 * ����������� ������
 */
public interface Repository {
    /**
     * ����� �� ������������ ������
     *
     * @param cdx - ������������ ������
     * @return - ���������� List c ������� ��� ������ List
     */
    List<CurrencyData> getAllDataByCdx(String cdx);

    /**
     * ����� �������� �� ������ � ����
     *
     * @param cdx  - ������������ ������
     * @param date - ����
     * @return Optional
     */
    Optional<CurrencyData> getDataByCdxAndDate(String cdx, String date);

    /**
     * ����� �������� �� ������������ ������ � �������� ������� ���������
     *
     * @param cdx          - ������������ ������
     * @param algBaseIndex - ������� ������ ���������
     * @return ���������� List c ������� ��� ������ List
     */
    List<CurrencyData> getDataByCdxAndLimitByALgBaseIndex(String cdx, int algBaseIndex);

}
