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
                controller.execute(buildRequest(arguments));
            }
        } catch (RuntimeException e) {
            log.info("CommanderImpl RuntimeException: {}", e.getMessage());
            e.printStackTrace();
            controller.addMessage(MESSAGE_WRONG_COMMAND + e.getMessage());
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

    private Request buildRequest(Arguments arguments) {
        if (!isValidParams(arguments)) {
            log.info("CommanderImpl buildRequest: arguments not valid");
            throw new IllegalArgumentException("arguments not valid");
        }
        String periodOrDate = arguments.getDate() != null ? arguments.getDate() : arguments.getPeriod();
        String output = Objects.isNull(arguments.getOutput()) ? OUTPUT_LIST : arguments.getOutput();
        return Request.builder()
                .currencyList(arguments.getRate())
                .periodOrDate(periodOrDate)
                .algorithm(arguments.getAlg())
                .output(output)
                .build();
    }

    /**
     * Валидация аргументов коммандной строки
     *
     * @param arguments - аргументы командной строки
     */
    private boolean isValidParams(Arguments arguments) {
        return isCorrectRateCurrency(arguments) &&
                isContainsPeriodOrDate(arguments) &&
                isContainsAlg(arguments) &&
                isValidDateAndOutput(arguments) &&
                isValidRateAndOutput(arguments);
    }

    /**
     * проверка валидности списка валют
     *
     * @param arguments - аргументы командной строки
     * @return false если аргументы не содержат списка валюты или при большем размере этого списка
     */
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

    /**
     * Период или дата
     *
     * @param arguments - аргументы командной строки
     * @return false если аргументы содержат period и date, или не содержит вовсе
     */
    private boolean isContainsPeriodOrDate(Arguments arguments) {
        boolean containsPeriodXORDate = Objects.nonNull(arguments.getPeriod()) ^ Objects.nonNull(arguments.getDate());
        if (!containsPeriodXORDate) {
            controller.addMessage(MESSAGE_PERIOD_OR_DATE);
            return false;
        }
        return true;
    }

    /**
     * Наличие алгоритма
     *
     * @param arguments - аргументы командной строки
     * @return false если аргументы не содержат алгоритма
     */
    private boolean isContainsAlg(Arguments arguments) {
        boolean hasAlg = Objects.isNull(arguments.getAlg());
        if (hasAlg) {
            controller.addMessage(MESSAGE_EMPTY_ALG);
        }
        return true;
    }

    /**
     * Отсутствие комманды -output при команде -date
     *
     * @param arguments - аргументы командной строки
     * @return false если аргументы содержат date и output команды.
     */
    private boolean isValidDateAndOutput(Arguments arguments) {
        boolean dateAndOutput = Objects.nonNull(arguments.getDate()) && Objects.nonNull(arguments.getOutput());
        if (dateAndOutput) {
            controller.addMessage(MESSAGE_DATE_WITH_OUTPUT);
            return false;
        }
        return true;
    }

    /**
     * Мультивалютность и выход
     *
     * @param arguments - аргументы командной строки
     * @return false Если аргументы содержат список с больше чем одной валютой и output = list.
     */
    private boolean isValidRateAndOutput(Arguments arguments) {
        boolean isOutputListAndMultirate = Objects.nonNull(arguments.getOutput()) &&
                arguments.getOutput().equals(OUTPUT_LIST) && arguments.getRate().size() > 1;
        if (isOutputListAndMultirate) {
            controller.addMessage(MESSAGE_MULTICURRENCY_IN_OUTPUT_LIST);
            return false;
        }
        return true;
    }


}
