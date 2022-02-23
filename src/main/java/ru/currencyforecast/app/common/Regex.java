package ru.currencyforecast.app.common;

import static ru.currencyforecast.app.common.Constant.*;

public class Regex {
    public static final String CSV_LINE_MATCHER = "(0[1-9]|[12][0-9]|3[01])[.](?:0[1-9]|1[012])[.](2[0-9][0-9][0-9])" +
            "[;]\\d+[,]\\d+" +
            "[;]((Турецкая лира)|(Евро)|(Доллар США))";

    public static final String COMMAND_MATCHER = "(" + RATE + ")\\s+"
            + "(" + USD + "|" + TRY + "|" + EUR + ")\\s+"
            + "(" + WEEK + "|" + TOMORROW + ")";
}
