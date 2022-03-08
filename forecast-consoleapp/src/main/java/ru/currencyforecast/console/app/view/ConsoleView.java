package ru.currencyforecast.console.app.view;

import ru.currencyforecast.lib.cli.Commander;
import ru.currencyforecast.lib.controller.Controller;
import ru.currencyforecast.lib.domain.Response;
import ru.currencyforecast.console.app.factory.ConsoleAppFactory;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.lib.util.PrintUtil;

import java.util.Scanner;

import static ru.currencyforecast.lib.common.Constant.COMMAND_EXIT;

public class ConsoleView {
    private final DataModel dataModel;
    private final Scanner scanner;
    private final Commander commandService;

    public ConsoleView() {
        this.dataModel = ConsoleAppFactory.getDataModel();
        this.scanner = ConsoleAppFactory.getScanner();
        Controller controller = ConsoleAppFactory.getController(dataModel);
        this.commandService = ConsoleAppFactory.getCommandService(controller);
    }

    public void launch() {
        while (true) {
            PrintUtil.printLine("Enter your command:");
            String command = scanner.nextLine().toLowerCase();
            if (command.equalsIgnoreCase(COMMAND_EXIT)) {
                return;
            }
            if (command.isEmpty()) {
                continue;
            }
            commandService.execute(command);
            printResult();
        }
    }

    private void printResult() {
        while (true) {
            if (!dataModel.isEmpty()) {
                Response response = dataModel.getResponseData();
                if (!response.isPicture()) {
                    PrintUtil.printLine((String) response.getMessage().getMessageData());
                } else {
                    PrintUtil.printLine("Console forecast does not support graph");
                }
                break;
            }
        }
    }
}
