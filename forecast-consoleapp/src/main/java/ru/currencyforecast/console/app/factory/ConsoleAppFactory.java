package ru.currencyforecast.console.app.factory;

import ru.currencyforecast.console.app.view.ConsoleView;
import ru.currencyforecast.lib.cli.Commander;
import ru.currencyforecast.lib.cli.CommanderImpl;
import ru.currencyforecast.lib.controller.Controller;
import ru.currencyforecast.lib.controller.ControllerImpl;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.lib.repository.CsvCurrencyRepository;
import ru.currencyforecast.lib.repository.Repository;
import ru.currencyforecast.lib.service.*;

import java.util.Scanner;

/**
 * Фабрика консольного приложения
 */
public class ConsoleAppFactory {
    private ConsoleAppFactory() {
    }

    public static ConsoleView getConsoleView() {
        Scanner scanner = new Scanner(System.in);
        Repository repository = new CsvCurrencyRepository();
        ForecastService forecastService = new ForecastServiceImpl();
        TrendService trendService = new TrendServiceImpl();
        DataModel dataModel = new DataModel();
        Service service = new ServiceImpl(repository, forecastService, trendService);
        Controller controller = new ControllerImpl(dataModel, service);
        Commander commander = new CommanderImpl(controller);
        return new ConsoleView(dataModel, scanner, commander);
    }
}
