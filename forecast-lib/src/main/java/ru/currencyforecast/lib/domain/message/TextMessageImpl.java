package ru.currencyforecast.lib.domain.message;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class TextMessageImpl implements Message {
    private final String text;

    @Override
    public String getMessageData() {
        return text;
    }
}
