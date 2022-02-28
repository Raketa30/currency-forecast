package ru.currencyforecast.app.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CSVReaderUtil {
    private CSVReaderUtil() {
    }

    public static List<String> read(String path, int linesCount) {
        LinkedList<String> readLines = new LinkedList<>();
        try (BufferedReader fileReader = new BufferedReader(
                new FileReader(Objects.requireNonNull(CSVReaderUtil.class.getClassLoader().getResource(path)).getFile()))) {
            fileReader.readLine();
            for (int i = 0; i < linesCount && fileReader.ready(); i++) {
                readLines.addFirst(fileReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readLines;
    }
}