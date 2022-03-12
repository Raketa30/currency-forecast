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
     * ��������� ���������� ���������� ������
     *
     * @param arguments - ��������� ��������� ������
     */
    private boolean isValidParams(Arguments arguments) {
        return isCorrectRateCurrency(arguments) &&
                isContainsPeriodOrDate(arguments) &&
                isContainsAlg(arguments) &&
                isValidDateAndOutput(arguments) &&
                isValidRateAndOutput(arguments);
    }

    /**
     * �������� ���������� ������ �����
     *
     * @param arguments - ��������� ��������� ������
     * @return false ���� ��������� �� �������� ������ ������ ��� ��� ������� ������� ����� ������
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
     * ������ ��� ����
     *
     * @param arguments - ��������� ��������� ������
     * @return false ���� ��������� �������� period � date, ��� �� �������� �����
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
     * ������� ���������
     *
     * @param arguments - ��������� ��������� ������
     * @return false ���� ��������� �� �������� ���������
     */
    private boolean isContainsAlg(Arguments arguments) {
        boolean hasAlg = Objects.isNull(arguments.getAlg());
        if (hasAlg) {
            controller.addMessage(MESSAGE_EMPTY_ALG);
        }
        return true;
    }

    /**
     * ���������� �������� -output ��� ������� -date
     *
     * @param arguments - ��������� ��������� ������
     * @return false ���� ��������� �������� date � output �������.
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
     * ���������������� � �����
     *
     * @param arguments - ��������� ��������� ������
     * @return false ���� ��������� �������� ������ � ������ ��� ����� ������� � output = list.
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
