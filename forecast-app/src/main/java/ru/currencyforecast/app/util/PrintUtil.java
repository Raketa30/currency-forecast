package ru.currencyforecast.app.util;

import ru.currencyforecast.app.domain.CurrencyData;

import java.util.List;

public class PrintUtil {
    private PrintUtil() {
    }

    public static void printLine(String line) {
        System.out.println(line);
    }

    public static void printDataList(List<CurrencyData> currencyDataList) {
        for (CurrencyData currencyData : currencyDataList) {
            System.out.println("    " + currencyData);
        }
        System.out.println();
    }
}