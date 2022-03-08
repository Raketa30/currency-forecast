package ru.currencyforecast.lib.cli;

import com.beust.jcommander.JCommander;
import ru.currencyforecast.lib.controller.Controller;

import java.util.List;
import java.util.Objects;

import static ru.currencyforecast.lib.common.Constant.*;

public class CommanderImpl implements Commander {
    private final Controller controller;
    private JCommander commander;

    public CommanderImpl(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String command) {
        Arguments arguments = extractArguments(command);
        if (arguments.isHelp()) {
            StringBuilder stringBuilder = new StringBuilder();
            commander.getUsageFormatter().usage(stringBuilder);
            controller.addMessage(stringBuilder.toString());
        } else {
            if (valid(arguments)) {
                controller.execute(arguments.getRate(), arguments.getPeriod(), arguments.getOutput(), arguments.getAlg());
            } else {
                controller.addMessage(MESSAGE_WRONG_COMMAND);
            }
        }
    }

    private Arguments extractArguments(String command) {
        Arguments arguments = new Arguments();
        commander = JCommander.newBuilder()
                .programName("Telegram Bot")
                .addObject(arguments)
                .build();
        commander.parse(command.split("\\s+"));
        return arguments;
    }

    private boolean valid(Arguments arguments) {
        boolean containsPeriodOrDate = containsPeriodOrDate(arguments);
        boolean cotainsRateCurrency = Objects.nonNull(arguments.getRate());
        boolean containsAlg = Objects.nonNull(arguments.getAlg());
        boolean containsOutput = Objects.nonNull(arguments.getOutput());
        return containsPeriodOrDate && cotainsRateCurrency && containsAlg && containsOutput;
    }

    private boolean containsPeriodOrDate(Arguments arguments) {
        boolean containsPeriodOrDate = Objects.nonNull(arguments.getPeriod()) || Objects.nonNull(arguments.getDate());
        boolean containsPeriodAndDate = Objects.nonNull(arguments.getPeriod()) && Objects.nonNull(arguments.getDate());
        return containsPeriodOrDate && !containsPeriodAndDate;
    }

    private boolean validList(Arguments arguments) {
        boolean currencyRateSize = arguments.getRate().size() == 1;
        boolean isList = arguments.getOutput().equals(OUTPUT_LIST);
        return currencyRateSize && isList;
    }


}
