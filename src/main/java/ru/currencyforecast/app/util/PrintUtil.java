package ru.currencyforecast.app.util;

import ru.currencyforecast.app.domain.Data;

import java.util.List;

public class PrintUtil {
    public static void printLine(String line) {
        System.out.println(line);
    }

    public static void printDataList(List<Data> dataList) {
        for (Data data : dataList) {
            System.out.println("    " + data);

        }
        System.out.println();
    }

    private PrintUtil(){}
}
