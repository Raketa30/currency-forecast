package ru.currencyforecast.app.repository;

import ru.currencyforecast.app.domain.Data;

import java.util.List;
import java.util.Optional;

public interface Repository {
    Optional<List<Data>> findAllByCurrencyLimitByDays(String currency, int days);
}
