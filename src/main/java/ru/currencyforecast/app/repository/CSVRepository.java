package ru.currencyforecast.app.repository;

import ru.currencyforecast.app.dao.CSVReaderUtil;
import ru.currencyforecast.app.domain.Data;

import java.util.List;
import java.util.Optional;

public class CSVRepository implements Repository {
    @Override
    public Optional<List<Data>> findAllByCurrencyLimitByDays(String currency, int days) {
        return CSVReaderUtil.findLastWeekData(currency, days);
    }


}
