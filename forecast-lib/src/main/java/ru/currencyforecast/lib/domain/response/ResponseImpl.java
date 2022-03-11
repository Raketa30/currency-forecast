package ru.currencyforecast.lib.domain.response;

import lombok.AllArgsConstructor;
import lombok.ToString;
import ru.currencyforecast.lib.domain.message.Message;

@AllArgsConstructor
@ToString
public class ResponseImpl implements Response {
    private final Message message;
    private final boolean isPicture;

    @Override
    public Message getMessage() {
        return message;
    }

    @Override
    public boolean isPicture() {
        return this.isPicture;
    }
}
