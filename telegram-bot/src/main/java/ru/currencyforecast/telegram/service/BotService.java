package ru.currencyforecast.telegram.service;

import lombok.AllArgsConstructor;
import ru.currencyforecast.lib.cli.Commander;

@AllArgsConstructor
public class BotService {
    private final Commander commander;

    public void execute(String command) {
        commander.execute(command);
    }
}
