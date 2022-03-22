package ru.currencyforecast.lib.repository;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий данных
 */
public interface Repository {
    /**
     * Поиск по наименованию валюты
     *
     * @param cdx - наименование валюты
     * @return - возвращает List c данными или пустой List
     */
    List<CurrencyData> getAllDataByCdx(String cdx);

    /**
     * Поиск значения по валюте и дате
     *
     * @param cdx  - наименование валюты
     * @param date - дата
     * @return Optional
     */
    Optional<CurrencyData> getDataByCdxAndDate(String cdx, String date);

    /**
     * Поиск значений по наименованию валюты и базовому индексу алгоритма
     *
     * @param cdx          - наименование валюты
     * @param algBaseIndex - базовый индекс алгоритма
     * @return возвращает List c данными или пустой List
     */
    List<CurrencyData> getDataByCdxAndLimitByALgBaseIndex(String cdx, int algBaseIndex);

    List<CurrencyData> getDataByCdxFromDate(String cdx, String date);

}
