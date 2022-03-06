package ru.currencyforecast.app.view;

import ru.currencyforecast.app.controller.Controller;
import ru.currencyforecast.app.factory.ConsoleAppFactory;
import ru.currencyforecast.app.model.DataModel;
import ru.currencyforecast.app.cli.ConsoleCommandService;
import ru.currencyforecast.app.util.PrintUtil;

import java.util.Scanner;

import static ru.currencyforecast.app.common.Constant.COMMAND_EXIT;
import static ru.currencyforecast.app.common.Constant.MESSAGE_WRONG_COMMAND;

public class ConsoleView {
    private final DataModel model;
    private final Controller controller;
    private final Scanner scanner;
    private final ConsoleCommandService commandService;

    public ConsoleView() {
        this.model = ConsoleAppFactory.getModel();
        this.scanner = ConsoleAppFactory.getScanner();
        this.controller = ConsoleAppFactory.getController(model);
        this.commandService = ConsoleAppFactory.getCommandService();
    }

    public void launch() {
        while (true) {
            PrintUtil.printLine("Enter your command:");
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase(COMMAND_EXIT)) {
                return;
            }
            if (command.isEmpty()) {
                continue;
            }

            String global = commandService.getGlobalCommand(command);
            String currency = commandService.getCurrencyCommand(command);
            String period = commandService.getPeriodCommand(command);

            if (isValid(global, currency, period)) {
                controller.execute(global, currency, period);
                printResult();
            } else {
                PrintUtil.printLine(MESSAGE_WRONG_COMMAND);
            }
        }
    }

    private boolean isValid(String global, String currency, String period) {
        return !global.isEmpty() && !currency.isEmpty() && !period.isEmpty();
    }

    private void printResult() {
        while (true) {
            if (!model.isEmpty()) {
                PrintUtil.printDataList(model.getCurrencyDataList());
                return;

            } else if (!model.isEmptyMessage()) {
                PrintUtil.printLine(model.getMessageAttribute());
                return;
            }
        }
    }
}
