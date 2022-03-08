package ru.currencyforecast.lib.common;

public class Constant {
    //charset
    public static final String CHARSET = "cp1251";
    //links
    public static final String USD_CSV_LINK = "csv_data/USD_F01_02_2005_T05_03_2022.csv";
    public static final String EUR_CSV_LINK = "csv_data/EUR_F01_02_2005_T05_03_2022.csv";
    public static final String TRY_CSV_LINK = "csv_data/TRY_F01_02_2005_T05_03_2022.csv";
    public static final String AMD_CSV_LINK = "csv_data/AMD_F01_02_2005_T05_03_2022.csv";
    public static final String BGN_CSV_LINK = "csv_data/BGN_F01_02_2005_T05_03_2022.csv";
    //for csv
    public static final String CSV_HEADER_NOMINAL = "nominal";
    public static final String CSV_HEADER_DATE = "data";
    public static final String CSV_HEADER_PRICE = "curs";
    public static final String CSV_HEADER_CURRENCY = "cdx";
    public static final char CSV_DELIMETER = ';';
    //commands
    public static final String COMMAND_RATE = "rate";
    public static final String COMMAND_EXIT = "exit";
    public static final String COMMAND_DATE = "-date";
    public static final String COMMAND_PERIOD = "-period";
    public static final String COMMAND_ALGORITH = "-alg";
    public static final String COMMAND_OUTPUT = "-output";
    //algorithm
    public static final String ALG_ACTUAL = "actual";
    public static final String ALG_MISTIC = "mistic";
    public static final String ALG_INTERNET = "internet";
    public static final String ALG_AVG = "average";
    public static final int ALGORITHM_BASE_DAYS = 7;
    //output
    public static final String OUTPUT_LIST = "list";
    public static final String OUTPUT_GRAPH = "graph";
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
    //messages
    public static final String MESSAGE_EMPTY_DATA = "Empty data in csv file";
    public static final String MESSAGE_WRONG_COMMAND = "Wrong command, for help use -help command ";
    public static final String MESSAGE_WRONG_ALG = "Wrong algorithm: ";

    private Constant() {
    }
}