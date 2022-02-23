package ru.currencyforecast.app.view;

import ru.currencyforecast.app.controller.Controller;
import ru.currencyforecast.app.domain.Data;
import ru.currencyforecast.app.factory.ConsoleAppFactory;
import ru.currencyforecast.app.model.Model;
import ru.currencyforecast.app.util.PrintUtil;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

import static ru.currencyforecast.app.common.Constant.EXIT;
import static ru.currencyforecast.app.common.Constant.RATE;

public class ConsoleView {
    private final Model model;
    private final Controller controller;
    private final Scanner scanner;

    public ConsoleView() {
        this.model = ConsoleAppFactory.getModel();
        this.scanner = ConsoleAppFactory.getScanner();
        this.controller = ConsoleAppFactory.getController(model);
    }

    public void init() {
        while (true) {
            PrintUtil.printLine("Enter your command:");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase(EXIT)) {
                return;
            }
            controller.getForecast(command);
            printResult();
        }
    }

    private void printResult() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<Object> task = () -> {
            while (true) {
                if (!model.isEmpty()) {
                    return model.getAttribute(RATE);
                }
                if (!model.isEmptyMessage()) {
                    return model.getMessageAttribute();
                }
            }
        };
        Future<Object> dataFuture = executorService.submit(task);
        try {
            Object dataFromModel = dataFuture.get();
            if (dataFromModel instanceof List) {
                PrintUtil.printDataList((List<Data>) dataFromModel);
            } else {
                PrintUtil.printLine((String) dataFromModel);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
