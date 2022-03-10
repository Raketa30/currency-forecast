package ru.currencyforecast.lib.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.UnixStyleUsageFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.currencyforecast.lib.controller.Controller;
import ru.currencyforecast.lib.domain.Request;

import java.util.List;
import java.util.Objects;

import static ru.currencyforecast.lib.common.Constant.*;

@Slf4j
@RequiredArgsConstructor
public class CommanderImpl implements Commander {
    private final Controller controller;
    private JCommander commander;

    @Override
    public void execute(String command) {
        try {
            Arguments arguments = extractArguments(command);
            if (arguments.isHelp()) {
                StringBuilder stringBuilder = new StringBuilder();
                commander.getUsageFormatter().usage(stringBuilder);
                controller.addMessage(stringBuilder.toString());
            } else {
                if (valid(arguments)) {
                    controller.execute(buildRequest(arguments));
                }
            }
        } catch (RuntimeException e) {
            log.info("CommanderImpl RuntimeException: {}",e.getMessage());
            controller.addMessage(MESSAGE_WRONG_COMMAND);
        }
    }

    private Arguments extractArguments(String command) {
        Arguments arguments = new Arguments();
        commander = JCommander.newBuilder()
                .addObject(arguments)
                .programName(COMMAND_RATE)
                .usageFormatter(new UnixStyleUsageFormatter(commander))
                .build();
        commander.parse(command.split("\\s+"));
        log.debug("CommanderImpl extractArgument: command '{}' parsed", commander.getParsedCommand());
        return arguments;
    }

    private boolean valid(Arguments arguments) {
        if (isContainsPeriodOrDate(arguments) && isContainsOutput(arguments) && isContainsAlg(arguments)) {
            boolean containsAlg = isContainsAlg(arguments);
            boolean correctRate = isCorrectRateCurrency(arguments);
            boolean singleRateAndList = isCorrectRateAndOutput(arguments);
            return correctRate
                    && containsAlg
                    && singleRateAndList;
        }
        log.debug("CommanderImpl arguments not valid");
        return false;
    }

    private Request buildRequest(Arguments arguments) {
        String periodOrDate = arguments.getDate() != null ? arguments.getDate() : arguments.getPeriod();
        return Request.builder()
                .currencyList(arguments.getRate())
                .periodOrDate(periodOrDate)
                .algorithm(arguments.getAlg())
                .output(arguments.getOutput())
                .build();
    }

    private boolean isContainsPeriodOrDate(Arguments arguments) {
        boolean containsPeriodOrDate = Objects.nonNull(arguments.getPeriod()) || Objects.nonNull(arguments.getDate());
        boolean containsPeriodAndDate = Objects.nonNull(arguments.getPeriod()) && Objects.nonNull(arguments.getDate());
        if (containsPeriodAndDate) {
            controller.addMessage(MESSAGE_PERIOD_OR_DATE);
            return false;
        }
        return containsPeriodOrDate;
    }

    private boolean isContainsOutput(Arguments arguments) {
        boolean hasOutput = Objects.nonNull(arguments.getOutput());
        if (!hasOutput) {
            controller.addMessage(MESSAGE_EMPTY_OUTPUT);
        }
        return hasOutput;
    }

    private boolean isContainsAlg(Arguments arguments) {
        boolean hasAlg = Objects.nonNull(arguments.getAlg());
        if (!hasAlg) {
            controller.addMessage(MESSAGE_EMPTY_OUTPUT);
        }
        return hasAlg;
    }

    private boolean isCorrectRateCurrency(Arguments arguments) {
        List<String> rate = arguments.getRate();
        if (Objects.isNull(rate)) {
            controller.addMessage(MESSAGE_NODATA_FOR_RATE);
            return false;
        } else if (rate.isEmpty() || rate.size() > MAX_OUTPUT) {
            controller.addMessage(MESSAGE_RATE_SIZE_ERROR + rate.size());
            return false;
        }
        return true;
    }

    private boolean isCorrectRateAndOutput(Arguments arguments) {
        int size = arguments.getRate().size();
        String output = arguments.getOutput();
        boolean correctRateAndOutput = (size == 1 && output.equals(OUTPUT_LIST)) ^
                (size >= 1 && output.equals(OUTPUT_GRAPH));
        if (!correctRateAndOutput) {
            controller.addMessage(MESSAGE_MULTICURRENCY_IN_OUTPUT_LIST);
        }
        return correctRateAndOutput;
    }
}
