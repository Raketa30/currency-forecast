package ru.currencyforecast.telegram.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.currencyforecast.lib.cli.Commander;

@Slf4j
@AllArgsConstructor
public class BotService {
    private final Commander commander;

    public void execute(String command) {
        log.debug("BotService execute command {}", command);
        commander.execute(command);
    }
}
