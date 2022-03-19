package ru.currencyforecast.lib.cli;

import com.beust.jcommander.JCommander;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.currencyforecast.lib.controller.Controller;
import ru.currencyforecast.lib.domain.ForecastRequest;
import ru.currencyforecast.lib.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static ru.currencyforecast.lib.common.Constant.*;

@Slf4j
@RequiredArgsConstructor
public class CommanderImpl implements Commander {
    private final Controller controller;
    private JCommander commander;

    @Override
    public boolean execute(String command) {
        try {
            Arguments arguments = extractArguments(command);
            if (isHelp(arguments)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(MESSAGE_USAGE_HELP);
                commander.getUsageFormatter().usage(stringBuilder);
                controller.addMessage(stringBuilder.toString());
            } else {
                controller.execute(buildRequest(arguments));
            }
            return true;
        } catch (RuntimeException e) {
            log.info("CommanderImpl RuntimeException: {}", e.getMessage());
            return false;
        }
    }

    private Arguments extractArguments(String command) {
        Arguments arguments = new Arguments();
        commander = JCommander.newBuilder()
                .addObject(arguments)
                .programName(COMMAND_RATE)
                .build();
        commander.parse(command.split("\\s+"));
        log.debug("CommanderImpl extractArgument: command '{}' parsed", this.commander.getParsedCommand());
        return arguments;
    }

    private boolean isHelp(Arguments arguments) {
        return arguments.isHelp() && otherArgsIsNull(arguments);
    }

    private ForecastRequest buildRequest(Arguments arguments) {
        if (!isValidParams(arguments)) {
            log.info("CommanderImpl buildRequest: arguments not valid");
            throw new IllegalArgumentException("arguments not valid");
        }
        String periodOrDate = arguments.getDate() != null ? arguments.getDate() : arguments.getPeriod();
        String output = Objects.isNull(arguments.getOutput()) ? OUTPUT_LIST : arguments.getOutput();
        return ForecastRequest.builder()
                .currencyList(arguments.getRate())
                .periodOrDate(periodOrDate)
                .algorithm(arguments.getAlg())
                .output(output)
                .build();
    }

    /**
     * @param arguments - аргументы
     * @return true - если все аргументы null
     */
    private boolean otherArgsIsNull(Arguments arguments) {
        return Objects.isNull(arguments.getOutput()) &&
                Objects.isNull(arguments.getAlg()) &&
                Objects.isNull(arguments.getRate()) &&
                Objects.isNull(arguments.getDate()) &&
                Objects.isNull(arguments.getPeriod());
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
                isValidRateAndOutput(arguments) &&
                isNoLongForecast(arguments);
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

    /**
     * @param arguments - аргументы командной строки
     * @return true если не превышен лимит в днях
     */
    private boolean isNoLongForecast(Arguments arguments) {
        String date = arguments.getDate();
        if (Objects.nonNull(date) && DateTimeUtil.isDate(date)) {
            LocalDate d = DateTimeUtil.getLocalDate(date);
            int i = DateTimeUtil.daysBetweenFromNowToDate(d);
            boolean inLimit = i <= FORECAST_LIMIT_DAYS;
            if (!inLimit) {
                controller.addMessage(MESSAGE_FORECAST_LIMIT + FORECAST_LIMIT_DAYS);
                return false;
            }
        }
        return true;
    }


}
