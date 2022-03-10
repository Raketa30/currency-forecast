package ru.currencyforecast.lib.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.UnixStyleUsageFormatter;
import lombok.RequiredArgsConstructor;
import ru.currencyforecast.lib.controller.Controller;

import java.util.Objects;

import static ru.currencyforecast.lib.common.Constant.MESSAGE_WRONG_COMMAND;

@RequiredArgsConstructor
public class CommanderImpl implements Commander {
    private final Controller controller;
    private JCommander commander;

    @Override
    public void execute(String command) {
        Arguments arguments = extractArguments(command);
        if (arguments.isHelp()) {
            StringBuilder stringBuilder = new StringBuilder();
            commander.getUsageFormatter().usage(stringBuilder);
            controller.addMessage(stringBuilder.toString());
        } else {
            if (valid(arguments)) {
                String periodOrDate = arguments.getPeriod() == null ? arguments.getDate() : arguments.getPeriod();
                controller.execute(arguments.getRate(), periodOrDate, arguments.getAlg(), arguments.getOutput());
            } else {
                controller.addMessage(MESSAGE_WRONG_COMMAND);
            }
        }
    }

    private Arguments extractArguments(String command) {
        Arguments arguments = new Arguments();
        commander = JCommander.newBuilder()
                .addObject(arguments)
                .programName("rate")
                .usageFormatter(new UnixStyleUsageFormatter(commander))
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
}
