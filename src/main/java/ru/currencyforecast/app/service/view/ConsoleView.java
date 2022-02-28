package ru.currencyforecast.app.service.view;

import ru.currencyforecast.app.controller.Controller;
import ru.currencyforecast.app.domain.CurrencyData;
import ru.currencyforecast.app.factory.ConsoleAppFactory;
import ru.currencyforecast.app.model.DataModel;
import ru.currencyforecast.app.service.CommandService;
import ru.currencyforecast.app.util.PrintUtil;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

import static ru.currencyforecast.app.common.Constant.COMMAND_EXIT;
import static ru.currencyforecast.app.common.Constant.MESSAGE_WRONG_COMMAND;

public class ConsoleView {
    private final DataModel model;
    private final Controller controller;
    private final Scanner scanner;
    private final CommandService commandService;

    public ConsoleView() {
        this.model = ConsoleAppFactory.getModel();
        this.scanner = ConsoleAppFactory.getScanner();
        this.controller = ConsoleAppFactory.getController(model);
        this.commandService = ConsoleAppFactory.getCommandService();
    }

    public void init() {
        PrintUtil.printLine("Enter your command:");
        while (true) {
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
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<Object> task = () -> {
            while (true) {
                if (!model.isEmpty()) {
                    return model.getCurrencyDataList();

                } else if (!model.isEmptyMessage()) {
                    return model.getMessageAttribute();
                }
            }
        };
        Future<Object> dataFuture = executorService.submit(task);
        try {
            Object dataFromModel = dataFuture.get();
            if (dataFromModel instanceof List) {
                PrintUtil.printDataList((List<CurrencyData>) dataFromModel);
            } else {
                PrintUtil.printLine((String) dataFromModel);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
