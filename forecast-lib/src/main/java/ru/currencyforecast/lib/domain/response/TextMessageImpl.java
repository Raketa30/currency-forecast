package ru.currencyforecast.lib.domain.response;

public class TextMessageImpl implements Message {
    private final String text;

    public TextMessageImpl(String text) {
        this.text = text;
    }

    @Override
    public String getMessageData() {
        return text;
    }
}
