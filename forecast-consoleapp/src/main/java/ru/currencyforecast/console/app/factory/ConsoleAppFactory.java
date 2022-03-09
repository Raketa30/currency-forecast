package ru.currencyforecast.console.app.factory;

import ru.currencyforecast.lib.cli.Commander;
import ru.currencyforecast.lib.cli.CommanderImpl;
import ru.currencyforecast.lib.controller.ControllerImpl;
import ru.currencyforecast.lib.controller.Controller;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.lib.repository.CurrencyRepository;
import ru.currencyforecast.lib.repository.Repository;
import ru.currencyforecast.lib.service.*;
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
        AlgorithmService algorithmService = new AlgorithmServiceImpl(average);
        ImageService imageService = new ImageServiceImpl();
        algorithmService.setAlgorithm(average);
        Service service = new ServiceImpl(repository, algorithmService, imageService);
        return new ControllerImpl(dataModel, service);
    }

    public static Scanner getScanner() {
        return new Scanner(System.in);
    }

    public static Commander getCommandService(Controller controller) {
        return new CommanderImpl(controller);
    }
}
