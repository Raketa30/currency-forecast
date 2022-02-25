package ru.currencyforecast.app.service;

import ru.currencyforecast.app.domain.Data;

import java.util.List;
import java.util.Optional;

public interface Service {
    Optional<List<Data>> getForecast(String currency, String period);
}
