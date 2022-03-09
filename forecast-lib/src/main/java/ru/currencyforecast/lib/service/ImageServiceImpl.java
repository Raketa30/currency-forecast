package ru.currencyforecast.lib.service;

import com.github.sh0nk.matplotlib4j.Plot;
import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImageServiceImpl implements ImageService {
    @Override
    public Plot getForecastTrend(Map<String, List<CurrencyData>> forecast) {
        Plot plot = Plot.create();
        plot.xlabel("days");
        plot.ylabel("price");
        plot.title(buildPlotTitle(new ArrayList<>()));
        return plot;
    }

    private String buildPlotTitle(List<CurrencyData> forecast) {
        return forecast.get(0).getCdx();
    }
}
