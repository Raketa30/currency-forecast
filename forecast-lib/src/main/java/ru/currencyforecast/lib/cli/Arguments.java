package ru.currencyforecast.lib.cli;

import com.beust.jcommander.Parameter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static ru.currencyforecast.lib.common.Constant.*;

/**
 * Аргументы приходящие в командной строке, обработанные JCommander
 */
@Getter
@NoArgsConstructor
public class Arguments {
    @Parameter(names = {COMMAND_RATE},
            description = "currency: USD,EUR,TRY,AMD,BGN",
            required = true)
    private List<String> rate;

    @Parameter(names = COMMAND_PERIOD,
            description = "period: week or month")
    private String period;

    @Parameter(names = COMMAND_DATE,
            description = "single date: 'tomorrow' or date in format 'dd.MM.yyyy'")
    private String date;

    @Parameter(names = COMMAND_ALGORITHM,
            description = "algorithms: actual, mistic, internet, average",
            required = true)
    private String alg;

    @Parameter(names = COMMAND_OUTPUT,
            description = "output: list or graph")
    private String output;

    @Parameter(names = COMMAND_HELP,
            description = "usage",
            help = true)
    private boolean help;
}
