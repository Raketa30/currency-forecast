package ru.currencyforecast.app.common;

public class Constant {
    //charset
    public static final String CHARSET = "cp1251";
    //links
    public static final String USD_CSV_LINK = "src/main/resources/csv_data/USD_F01_02_2005_T05_03_2022.csv";
    public static final String EUR_CSV_LINK = "src/main/resources/csv_data/EUR_F01_02_2005_T05_03_2022.csv";
    public static final String TRY_CSV_LINK = "src/main/resources/csv_data/TRY_F01_02_2005_T05_03_2022.csv";
    public static final String AMD_CSV_LINK = "src/main/resources/csv_data/AMD_F01_02_2005_T05_03_2022.csv";
    public static final String BGN_CSV_LINK = "src/main/resources/csv_data/BGN_F01_02_2005_T05_03_2022.csv";
    //for csv
    public static final String CSV_HEADER_NOMINAL = "nominal";
    public static final String CSV_HEADER_DATE = "data";
    public static final String CSV_HEADER_PRICE = "curs";
    public static final String CSV_HEADER_CURRENCY = "cdx";
    public static final char CSV_DELIMETER = ';';

    public static final String COMMAND_RATE = "rate";
    public static final String COMMAND_EXIT = "exit";

    public static final String CURRENCY_USD = "usd";
    public static final String CURRENCY_EUR = "eur";
    public static final String CURRENCY_TRY = "try";
    public static final String CURRENCY_AMD = "amd";
    public static final String CURRENCY_BGN = "bgn";

    public static final String FORECAST_DEPTH_WEEK = "week";
    public static final int FORECAST_DEPTH_WEEK_INDEX = 7;

    public static final String FORECAST_DEPTH_TOMMOROW = "tomorrow";
    public static final int FORECAST_DEPTH_TOMORROW_INDEX = 1;

    public static final int ALGORITHM_BASE_DAYS = 7;
    //messages
    public static final String MESSAGE_EMPTY_DATA = "Empty data in csv file";
    public static final String MESSAGE_WRONG_COMMAND = "Wrong command: ";

    private Constant() {
    }
}
