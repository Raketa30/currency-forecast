package ru.currencyforecast.lib.domain.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseImpl implements Response {
    private final Message message;
    private final boolean isPicture;

    @Override
    public Message getMessage() {
        return message;
    }

    @Override
    public boolean isPicture() {
        return false;
    }
}
