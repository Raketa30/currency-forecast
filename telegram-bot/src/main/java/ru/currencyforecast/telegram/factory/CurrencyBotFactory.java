package ru.currencyforecast.telegram.factory;

import ru.currencyforecast.lib.cli.Commander;
import ru.currencyforecast.lib.cli.CommanderImpl;
import ru.currencyforecast.lib.controller.ControllerImpl;
import ru.currencyforecast.lib.controller.Controller;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.lib.repository.CurrencyRepository;
import ru.currencyforecast.lib.repository.Repository;
import ru.currencyforecast.lib.service.*;
import ru.currencyforecast.lib.service.algorithm.AverageAlgorithmImpl;
import ru.currencyforecast.telegram.service.BotService;

public class CurrencyBotFactory {
    private CurrencyBotFactory() {
    }

    public static BotService getBotService(DataModel dataModel) {
        Repository repository = new CurrencyRepository();
        AlgorithmService algorithmService = new AlgorithmServiceImpl(new AverageAlgorithmImpl());
        ImageService imageService = new ImageServiceImpl();
        Service service = new ServiceImpl(repository, algorithmService, imageService);
        Controller controller = new ControllerImpl(dataModel, service);
        Commander commander = new CommanderImpl(controller);
        return new BotService(commander);
    }

    public static DataModel getDataModel() {
        return new DataModel();
    }
}
