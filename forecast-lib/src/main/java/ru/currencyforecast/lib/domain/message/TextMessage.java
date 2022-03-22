package ru.currencyforecast.lib.domain.message;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class TextMessage implements AbstractMessage<String> {
    private final String text;

    @Override
    public String getData() {
        return text;
    }
}
