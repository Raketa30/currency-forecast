package ru.currencyforecast.telegram.service;

import ru.currencyforecast.lib.cli.Commander;

public class BotService {
    private final Commander commander;

    public BotService(Commander commander) {
        this.commander = commander;
    }

    public void execute(String command) {
        commander.execute(command);
    }
}
