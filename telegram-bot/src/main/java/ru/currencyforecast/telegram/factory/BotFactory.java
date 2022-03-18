package ru.currencyforecast.telegram.factory;

import lombok.extern.slf4j.Slf4j;
import ru.currencyforecast.lib.cli.Commander;
import ru.currencyforecast.lib.cli.CommanderImpl;
import ru.currencyforecast.lib.controller.Controller;
import ru.currencyforecast.lib.controller.ControllerImpl;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.lib.repository.CsvCurrencyRepository;
import ru.currencyforecast.lib.repository.Repository;
import ru.currencyforecast.lib.service.*;
import ru.currencyforecast.telegram.bot.CurrencyBot;
import ru.currencyforecast.telegram.service.BotService;

/**
 * Фабричный класс телеграм бота
 */
@Slf4j
public class BotFactory {
    private BotFactory() {
    }

    public static CurrencyBot getCurrencyBot() {
        Repository repository = new CsvCurrencyRepository();
        ForecastService forecastService = new ForecastServiceImpl();
        TrendService trendService = new TrendServiceImpl();
        DataModel dataModel = new DataModel();
        Service service = new ServiceImpl(repository, forecastService, trendService);
        Controller controller = new ControllerImpl(dataModel, service);
        Commander commander = new CommanderImpl(controller);
        BotService botService = new BotService(commander);
        return new CurrencyBot(botService, dataModel);
    }
}
