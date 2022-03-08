package ru.currencyforecast.telegram.factory;

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
import ru.currencyforecast.lib.service.algorithm.AverageAlgorithmImpl;
import ru.currencyforecast.telegram.service.BotService;

public class CurrencyBotFactory {
    private CurrencyBotFactory(){}

    public static BotService getBotService(DataModel dataModel) {
        Repository repository = new CurrencyRepository();
        ForecastService forecastService = new AlgorithmServiceImpl(new AverageAlgorithmImpl());
        Service service = new ConsoleServiceImpl(repository, forecastService);
        Controller controller = new ConsoleControllerImpl(dataModel, service);
        Commander commander = new CommanderImpl(controller);
        return new BotService(commander);
    }

    public static DataModel getDataModel() {
        return new DataModel();
    }
}
