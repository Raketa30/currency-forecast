package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.Response;

public interface Service {
    Response getForecast(String currency, String period, String algroithm);
}
