package ru.currencyforecast.app.common;

public class Constant {
    //links
    public static final String USD_CSV_LINK = "csv_data/USD_F01_02_2002_T01_02_2022.csv";
    public static final String EUR_CSV_LINK = "csv_data/EUR_F01_02_2002_T01_02_2022.csv";
    public static final String TRY_CSV_LINK = "csv_data/TRY_F01_02_2002_T01_02_2022.csv";
    //for csv
    public static final String CSV_HEADER_DATE = "data";
    public static final String CSV_HEADER_PRICE = "curs";
    public static final String CSV_HEADER_CURRENCY = "cdx";
    public static final String CSV_DELIMETER = ";";

    public static final String COMMAND_RATE = "rate";
    public static final String COMMAND_EXIT = "exit";

    public static final String CURRENCY_USD = "usd";
    public static final String CURRENCY_EUR = "eur";
    public static final String CURRENCY_TRY = "try";

    public static final String FORECAST_DEPTH_WEEK = "week";
    public static final String FORECAST_DEPTH_TOMMOROW = "tomorrow";


    public static final int ALGORITHM_BASE_DAYS = 7;
    //messages
    public static final String MESSAGE_EMPTY_DATA = "Empty data in csv file";
    public static final String MESSAGE_WRONG_COMMAND = "Wrong command: ";

    private Constant() {
    }
}
