package ru.currencyforecast.app.cli;

import java.util.List;

import static ru.currencyforecast.app.common.Constant.*;

public class ConsoleCommandService {
    private final List<String> global;
    private final List<String> currency;
    private final List<String> period;

    public ConsoleCommandService() {
        this.global = List.of(COMMAND_RATE, COMMAND_EXIT);
        this.currency = List.of(CURRENCY_USD, CURRENCY_TRY, CURRENCY_EUR);
        this.period = List.of(FORECAST_DEPTH_WEEK, FORECAST_DEPTH_TOMMOROW);
    }

    public String getCurrencyCommand(String command) {
        String currencyCmd = parseCommand(1, command);
        return isCurrency(currencyCmd) ? currencyCmd : "";
    }

    private String parseCommand(int index, String command) {
        String[] commandArray = command.toLowerCase().split("\\s+");
        try {
            return commandArray[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "";
        }
    }

    private boolean isCurrency(String currency) {
        return this.currency.contains(currency);
    }

    public String getGlobalCommand(String command) {
        String globalCmd = parseCommand(0, command);
        return isGlobal(globalCmd) ? globalCmd : "";
    }

    private boolean isGlobal(String command) {
        return this.global.contains(command);
    }

    public String getPeriodCommand(String command) {
        String periodCmd = parseCommand(2, command);
        return isPeriod(periodCmd) ? periodCmd : "";
    }

    private boolean isPeriod(String period) {
        return this.period.contains(period);
    }

}
