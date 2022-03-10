package ru.currencyforecast.lib.domain.response;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;

@AllArgsConstructor
@ToString
public class GraphMessageImpl implements Message {
    private final Path pathToImg;

    @Override
    public Path getMessageData() {
        return pathToImg;
    }
}
