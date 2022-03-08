package ru.currencyforecast.lib.domain;

public class ResponseImpl implements Response {
    private final Message message;
    private final boolean isPicture;

    public ResponseImpl(Message message, boolean isPicture) {
        this.message = message;
        this.isPicture = isPicture;
    }

    @Override
    public Message getMessage() {
        return message;
    }

    @Override
    public boolean isPicture() {
        return false;
    }
}
