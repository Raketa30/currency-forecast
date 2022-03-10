package ru.currencyforecast.console.app.view;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.currencyforecast.lib.cli.Commander;
import ru.currencyforecast.lib.domain.response.Response;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.lib.util.PrintUtil;

import java.util.Scanner;

import static ru.currencyforecast.lib.common.Constant.COMMAND_EXIT;

/**
 *  ласс представл€ющий отображение консольного приложени€
 */
@AllArgsConstructor
public class ConsoleView {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleView.class);

    private final DataModel model;
    private final Scanner scanner;
    private final Commander commander;

    public void launchConsole() {
        while (true) {
            PrintUtil.printLine("Enter your command:");
            String command = scanner.nextLine().toLowerCase();
            logger.info("¬ведена команда: {}", command);
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
            if (!model.isEmpty()) {
                Response response = model.getResponseData();
                if (!response.isPicture()) {
                    PrintUtil.printLine((String) response.getMessage().getMessageData());
                } else {
                    PrintUtil.printLine("Console forecast does not support graph");
                }
                logger.info("ќтвет получен");
                break;
            }
        }
    }
}
