package ru.currencyforecast.lib.service;

import ru.currencyforecast.lib.domain.response.Response;

import java.util.List;

public interface Service {
    Response getListForecast(String currency, String period, String algroithm);

    Response getGraphForecast(List<String> currency, String period, String algroithm);
}
