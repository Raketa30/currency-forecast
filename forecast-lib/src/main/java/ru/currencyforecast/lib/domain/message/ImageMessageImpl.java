package ru.currencyforecast.lib.domain.message;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.awt.image.BufferedImage;

@AllArgsConstructor
@ToString
public class ImageMessageImpl implements Message {
    private final BufferedImage file;

    @Override
    public BufferedImage getMessageData() {
        return file;
    }
}
