package ru.currencyforecast.lib.common;

/**
 * Константы используемые в сервисе
 */
public class Constant {
    //charset
    public static final String CHARSET = "cp1251";
    //links
    public static final String USD_CSV_LINK = "csv_data/USD_F01_02_2005_T05_03_2022.csv";
    public static final String EUR_CSV_LINK = "csv_data/EUR_F01_02_2005_T05_03_2022.csv";
    public static final String TRY_CSV_LINK = "csv_data/TRY_F01_02_2005_T05_03_2022.csv";
    public static final String AMD_CSV_LINK = "csv_data/AMD_F01_02_2005_T05_03_2022.csv";
    public static final String BGN_CSV_LINK = "csv_data/BGN_F01_02_2005_T05_03_2022.csv";
    public static final String PLOT_IMAGE_SAVING_LINK = "forecast-lib/temp-trends/trend.jpg";
    //for csv
    public static final String CSV_HEADER_NOMINAL = "nominal";
    public static final String CSV_HEADER_DATE = "data";
    public static final String CSV_HEADER_PRICE = "curs";
    public static final String CSV_HEADER_CURRENCY = "cdx";
    public static final char CSV_DELIMETER = ';';
    public static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy";
    //commands
    public static final String COMMAND_RATE = "rate";
    public static final String COMMAND_EXIT = "-exit";
    public static final String COMMAND_DATE = "-date";
    public static final String COMMAND_PERIOD = "-period";
    public static final String COMMAND_ALGORITHM = "-alg";
    public static final String COMMAND_OUTPUT = "-output";
    public static final String COMMAND_HELP = "-help";
    //algorithm
    public static final String ALG_ACTUAL = "actual";
    public static final String ALG_MISTIC = "mistic";
    public static final String ALG_INTERNET = "internet";
    public static final String ALG_AVG = "average";

    public static final int ALG_ACTUAL_BASE = 1096; //3 year * 365 days + 1ay
    public static final int ALG_MISTIC_BASE = 365; //29.5 days every fullmoon * 3 times
    public static final int ALG_INTERNET_BASE = 30; //last month
    public static final int ALG_AVG_BASE = 7; //last 7 result
    //output
    public static final String OUTPUT_LIST = "list";
    public static final String OUTPUT_GRAPH = "graph";
    public static final int MAX_OUTPUT = 5;
    //currency
    public static final String CURRENCY_USD = "usd";
    public static final String CURRENCY_EUR = "eur";
    public static final String CURRENCY_TRY = "try";
    public static final String CURRENCY_AMD = "amd";
    public static final String CURRENCY_BGN = "bgn";
    //depth
    public static final String FORECAST_DEPTH_MONTH = "month";
    public static final String FORECAST_DEPTH_WEEK = "week";
    public static final String FORECAST_DEPTH_TOMMOROW = "tomorrow";
    public static final int FORECAST_DEPTH_MONTH_INDEX = 30;
    public static final int FORECAST_DEPTH_WEEK_INDEX = 7;
    public static final int FORECAST_DEPTH_TOMORROW_INDEX = 1;
    public static final int FORECAST_LIMIT_DAYS = 2000;
    //messages
    public static final String WRONG_CURRENCY_OR_EMPTY_DATA_IN_CSV_FILE = "Wrong currency or Empty data in csv file";
    public static final String MESSAGE_WRONG_COMMAND = "Wrong command format, for help use -help command: ";
    public static final String MESSAGE_WRONG_ALG = "Wrong algorithm: ";
    public static final String MESSAGE_WRONG_PERIOD = "Wrong period: ";
    public static final String MESSAGE_FORECAST_LIMIT = "Allo Yoba eto ti? Limit days: ";
    public static final String MESSAGE_WRONG_OUTPUT = "Wrong output: ";
    public static final String MESSAGE_MULTICURRENCY_IN_OUTPUT_LIST = "Multicurrency not supported in output -list";
    public static final String MESSAGE_NODATA_FOR_RATE = "Rate list is empty";
    public static final String MESSAGE_RATE_SIZE_ERROR = "Rate size : ";
    public static final String MESSAGE_PERIOD_OR_DATE = "Wrong period or date";
    public static final String MESSAGE_EMPTY_OUTPUT = "Empty output";
    public static final String MESSAGE_EMPTY_ALG = "Empty algorihm";
    public static final String MESSAGE_WRONG_IMAGE = "Wrong image: ";
    public static final String MESSAGE_DATE_WITH_OUTPUT = "Command -date not required -output";
    public static final String MESSAGE_DATE_IS_PAST = "This date is past: ";
    public static final String MESSAGE_USAGE_HELP = "Command input formats: \n" +
            "For single rate: \n" +
            "rate [options] -alg [options] -date [options]\n" +
            "For long rate: \n" +
            "rate [options] -alg [options] -period [options] -output [options]\n";

    private Constant() {
    }
}
