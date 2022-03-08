package ru.currencyforecast.console.app.factory;

import ru.currencyforecast.lib.cli.Commander;
import ru.currencyforecast.lib.cli.CommanderImpl;
import ru.currencyforecast.lib.controller.ConsoleControllerImpl;
import ru.currencyforecast.lib.controller.Controller;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.lib.repository.CurrencyRepository;
import ru.currencyforecast.lib.repository.Repository;
import ru.currencyforecast.lib.service.AlgorithmServiceImpl;
import ru.currencyforecast.lib.service.ConsoleServiceImpl;
import ru.currencyforecast.lib.service.ForecastService;
import ru.currencyforecast.lib.service.Service;
import ru.currencyforecast.lib.service.algorithm.Algorithm;
import ru.currencyforecast.lib.service.algorithm.AverageAlgorithmImpl;

import java.util.Scanner;

public class ConsoleAppFactory {
    private ConsoleAppFactory() {
    }

    public static DataModel getDataModel() {
        return new DataModel();
    }

    public static Controller getController(DataModel dataModel) {
        Repository repository = new CurrencyRepository();
        Algorithm average = new AverageAlgorithmImpl();
        ForecastService forecastService = new AlgorithmServiceImpl(average);
        forecastService.setAlgorithm(average);
        Service service = new ConsoleServiceImpl(repository, forecastService);
        return new ConsoleControllerImpl(dataModel, service);
    }

    public static Scanner getScanner() {
        return new Scanner(System.in);
    }

    public static Commander getCommandService(Controller controller) {
        return new CommanderImpl(controller);
    }
}
