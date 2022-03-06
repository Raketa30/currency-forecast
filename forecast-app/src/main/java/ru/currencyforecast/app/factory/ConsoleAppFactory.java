package ru.currencyforecast.app.factory;

import ru.currencyforecast.app.controller.Controller;
import ru.currencyforecast.app.controller.ConsoleControllerImpl;
import ru.currencyforecast.app.model.DataModel;
import ru.currencyforecast.app.repository.CurrencyRepository;
import ru.currencyforecast.app.repository.Repository;
import ru.currencyforecast.app.service.*;
import ru.currencyforecast.app.service.algorithm.AverageAlgorithm;
import ru.currencyforecast.app.service.algorithm.AlgorithmService;
import ru.currencyforecast.app.service.ConsoleCommandService;

import java.util.Scanner;

public class ConsoleAppFactory {

    private ConsoleAppFactory() {
    }

    public static DataModel getModel() {
        return new DataModel();
    }

    public static Controller getController(DataModel model) {
        Repository repository = new CurrencyRepository();
        AlgorithmService average = new AverageAlgorithm();
        ForecastService forecastService = new AlgorithmServiceImpl(average);
        forecastService.setAlgorithm(average);
        Service service = new ConsoleServiceImpl(repository, forecastService);
        return new ConsoleControllerImpl(model, service);
    }

    public static Scanner getScanner() {
        return new Scanner(System.in);
    }

    public static ConsoleCommandService getCommandService() {
        return new ConsoleCommandService();
    }
}
