package ru.currencyforecast.lib.domain.response;

import ru.currencyforecast.lib.domain.message.Message;

/**
 * »нтерфейс представл€ющий ответ, содержащий абстрактное сообщение и флаг содержани€ картинки
 * требуютс€ улучшение с помощью дженериков
 */
public interface Response {
    Message getMessage();

    boolean isPicture();
}
