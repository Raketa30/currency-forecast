package ru.currencyforecast.lib.service;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import lombok.extern.slf4j.Slf4j;
import ru.currencyforecast.lib.domain.CurrencyData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.currencyforecast.lib.common.Constant.PLOT_IMAGE_SAVING_LINK;

@Slf4j
public class TrendServiceImpl implements TrendService {
    @Override
    public Optional<File> getForecastTrend(Map<String, List<CurrencyData>> forecast) {
        Plot plot = createPlot(forecast);
        if (isSuccesfullySavedImage(plot)) {
            return Optional.of(new File(PLOT_IMAGE_SAVING_LINK));
        }
        log.debug("TrendServiceImpl getForecastTrend Optional emptyFile");
        return Optional.empty();
    }

    private Plot createPlot(Map<String, List<CurrencyData>> forecast) {
        Plot plot = Plot.create();
        plot.title("CURRENCY");
        plot.xlabel("DAYS");
        plot.ylabel("PRICE");
        for (Map.Entry<String, List<CurrencyData>> entry : forecast.entrySet()) {
            List<Double> prices = entry.getValue().stream().map(CurrencyData::getCurs).collect(Collectors.toList());
            plot.plot().add(prices).linestyle("-").label(entry.getValue().get(0).getCdx());
        }
        plot.legend().loc("upper right");
        log.debug("TrendServiceImpl createPlot plot created");
        return plot;
    }

    private boolean isSuccesfullySavedImage(Plot plot) {
        try {
            if (Files.notExists(Paths.get(PLOT_IMAGE_SAVING_LINK))) {
                Files.createFile(Paths.get(PLOT_IMAGE_SAVING_LINK));
            }
            plot.savefig(PLOT_IMAGE_SAVING_LINK).dpi(200);
            plot.executeSilently();
            log.debug("TrendServiceImpl isSuccesfullySavedImage image saved");
            return true;
        } catch (IOException | PythonExecutionException e) {
            log.info("TrendServiceImpl image not saved, excp: {}", e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

}
