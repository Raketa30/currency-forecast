package ru.currencyforecast.lib.cli;

import com.beust.jcommander.Parameter;

import java.util.List;

import static ru.currencyforecast.lib.common.Constant.*;

public class Arguments {
    @Parameter(names = {COMMAND_RATE},
            description = "Currency rate for: USD, EUR, TRY, AMD, BGN",
            required = true)
    private List<String> rate;

    @Parameter(names = COMMAND_PERIOD,
            description = "Rate period: week or month")
    private String period;

    @Parameter(names = COMMAND_DATE,
            description = "Rate date: tomorrow or date in format dd.MM.yyyy")
    private String date;

    @Parameter(names = COMMAND_ALGORITHM,
            description = "Rate algorithm: actual, mistic, internet, average",
            required = true)
    private String alg;

    @Parameter(names = COMMAND_OUTPUT,
            description = "Output format: list or graph",
            required = true)
    private String output;

    @Parameter(names = COMMAND_HELP,
            description = "Usage",
            help = true)
    private boolean help;

    public String getAlg() {
        return alg;
    }

    public String getDate() {
        return date;
    }

    public String getOutput() {
        return output;
    }

    public String getPeriod() {
        return period;
    }

    public List<String> getRate() {
        return rate;
    }

    public boolean isHelp() {
        return help;
    }
}
