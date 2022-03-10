package ru.currencyforecast.lib.domain.response;

/**
 * »нтерфейс представл€ющий ответ, содержащий абстрактное сообщение и флаг содержани€ картинки
 * требуютс€ улучшение с помощью дженериков
 */
public interface Response {
    Message getMessage();

    boolean isPicture();
}
