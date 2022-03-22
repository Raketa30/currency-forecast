package ru.currencyforecast.lib.domain.message;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.awt.image.BufferedImage;

@AllArgsConstructor
@ToString
public class ImageMessage implements AbstractMessage<BufferedImage> {
    private final BufferedImage bufferedImage;

    @Override
    public BufferedImage getData() {
        return bufferedImage;
    }
}
