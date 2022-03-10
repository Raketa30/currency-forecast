package ru.currencyforecast.lib.domain.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TextMessageImpl implements Message {
    private final String text;

    @Override
    public String getMessageData() {
        return text;
    }
}
