package ru.currencyforecast.app.model;

import ru.currencyforecast.app.domain.Data;

import java.util.List;

public interface Model{
    List<Data> getAttribute(String id);

    String getMessageAttribute();

    void addAttribute(String id, List<Data> data);

    void addMessageAttribute(String message);

    boolean isEmpty();

    boolean isEmptyMessage();

    void clear();
}
