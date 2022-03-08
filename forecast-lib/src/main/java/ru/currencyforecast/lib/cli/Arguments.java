package ru.currencyforecast.lib.cli;

import com.beust.jcommander.Parameter;

import java.util.List;

public class Arguments {
//            "rate TRY -date tomorrow -alg moon"
//            "rate TRY -date 22.02.2030 -alg moon"
//            "rate USD -period week -alg moon -output list"
//            "rate USD,TRY -period month -alg moon -output graph"
    @Parameter(names = {"rate"},
            description = "Currency rate",
            required = true)
    private List<String> rate;

    @Parameter(names = "-period",
            description = "Rate period: week or month")
    private String period;

    @Parameter(names = "-date",
            description = "Rate date: tomorrow or date in format dd.MM.yyyy")
    private String date;

    @Parameter(names = "-alg",
            description = "Rate algorithm: actual, mistic, internet",
            required = true)
    private String alg;

    @Parameter(names = "-output",
            description = "Output format: list or graph")
    private String output;

    @Parameter(names = {"-help"},
            description = "Usage:",
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
