package ru.currencyforecast.app.model;

import ru.currencyforecast.app.domain.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class DataModel implements Model {
    private final Map<String, List<Data>> model;
    private String messageAttribute;

    public DataModel() {
        this.model = new ConcurrentHashMap<>();
        this.messageAttribute = "";
    }

    @Override
    public List<Data> getAttribute(String id) {
        return model.get(id);
    }

    @Override
    public String getMessageAttribute() {
        return messageAttribute;
    }

    @Override
    public void addAttribute(String forecastId, List<Data> forecast) {
        clear();
        model.put(forecastId, forecast);
    }

    @Override
    public void addMessageAttribute(String message) {
        this.messageAttribute = message;
    }

    @Override
    public boolean isEmpty() {
        return model.isEmpty();
    }

    @Override
    public boolean isEmptyMessage() {
        return Objects.isNull(messageAttribute) || messageAttribute.equals("");
    }

    @Override
    public void clear() {
        model.clear();
        messageAttribute = "";
    }
}
