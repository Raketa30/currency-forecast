package ru.currencyforecast.app.factory;

import ru.currencyforecast.app.controller.Controller;
import ru.currencyforecast.app.controller.ConsoleControllerImpl;
import ru.currencyforecast.app.model.DataModel;
import ru.currencyforecast.app.model.Model;
import ru.currencyforecast.app.repository.CSVRepository;
import ru.currencyforecast.app.repository.Repository;
import ru.currencyforecast.app.service.ForecastService;
import ru.currencyforecast.app.service.AlgorithmServiceImpl;
import ru.currencyforecast.app.service.ConsoleServiceImpl;
import ru.currencyforecast.app.service.Service;
import ru.currencyforecast.app.service.algorithm.AverageAlg;
import ru.currencyforecast.app.service.algorithm.ForecastAlgorithm;

import java.util.Scanner;

public class ConsoleAppFactory {

    private ConsoleAppFactory() {
    }

    public static Model getModel() {
        return new DataModel();
    }

    public static Controller getController(Model model) {
        Repository repository = new CSVRepository();
        ForecastAlgorithm average = new AverageAlg();
        ForecastService forecastService = new AlgorithmServiceImpl(average);

        forecastService.setAlgorithm(average);
        Service service = new ConsoleServiceImpl(repository, forecastService);
        return new ConsoleControllerImpl(model, service);
    }

    public static Scanner getScanner() {
        return new Scanner(System.in);
    }
}
