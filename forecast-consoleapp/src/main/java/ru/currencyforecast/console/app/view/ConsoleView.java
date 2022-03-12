package ru.currencyforecast.console.app.view;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.currencyforecast.lib.cli.Commander;
import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.domain.response.Response;
import ru.currencyforecast.lib.domain.response.ResponseType;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.lib.util.PrintUtil;

import java.util.List;
import java.util.Scanner;

import static ru.currencyforecast.lib.common.Constant.COMMAND_EXIT;

/**
 * Класс представляющий отображение консольного приложения
 */
@Slf4j
@AllArgsConstructor
public class ConsoleView {
    private final DataModel model;
    private final Scanner scanner;
    private final Commander commander;

    public void launchConsole() {
        log.debug("ConsoleView launchConsole");
        while (true) {
            PrintUtil.printLine("Enter your command:");
            String command = scanner.nextLine().toLowerCase();
            log.debug("Введена команда: {}", command);
            if (command.equalsIgnoreCase(COMMAND_EXIT)) {
                return;
            }
            if (command.isEmpty()) {
                continue;
            }
            commander.execute(command);
            printResult();
        }
    }

    private void printResult() {
        while (true) {
            if (model.isNotEmpty()) {
                Response response = model.getResponse();
                ResponseType type = response.getType();
                if (type == ResponseType.IMAGE) {
                    PrintUtil.printLine("Console forecast does not support graph");
                } else if (type == ResponseType.DATA) {
                    PrintUtil.printDataList((List<CurrencyData>) response.getMessage().getData());
                } else {
                    PrintUtil.printLine((String) response.getMessage().getData());
                }
                log.debug("ConsoleView printResult Response: {}", response.getClass().getName());
                break;
            }
        }
    }
}
