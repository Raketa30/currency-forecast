package ru.currencyforecast.lib.domain.response;

import lombok.AllArgsConstructor;

import java.nio.file.Path;

@AllArgsConstructor
public class GraphMessageImpl implements Message {
    private final Path pathToImg;

    @Override
    public Path getMessageData() {
        return pathToImg;
    }
}
