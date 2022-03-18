package ru.currencyforecast.lib.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.currencyforecast.lib.domain.message.AbstractMessage;

@AllArgsConstructor
@Getter
public class ForecastResponse<T> implements Response {
    private final ResponseType type;
    private final AbstractMessage<T> message;
}
