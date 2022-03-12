package ru.currencyforecast.lib.domain.response;

import ru.currencyforecast.lib.domain.message.AbstractMessage;

/**
 * »нтерфейс представл€ющий ответ, содержащий абстрактное сообщение и тип ответа
 */
public interface Response {
    <T> AbstractMessage<T> getMessage();

    ResponseType getType();
}
