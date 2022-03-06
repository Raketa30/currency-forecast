package ru.currencyforecast.app.model;

import ru.currencyforecast.app.domain.CurrencyData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataModel {
    private final List<CurrencyData> currencyDataList;
    private String messageAttribute;

    public DataModel() {
        this.currencyDataList = new ArrayList<>();
        this.messageAttribute = "";
    }

    public void addAttribute(List<CurrencyData> forecast) {
        currencyDataList.clear();
        currencyDataList.addAll(forecast);
    }

    public void addMessageAttribute(String message) {
        this.messageAttribute = message;
    }

    public List<CurrencyData> getCurrencyDataList() {
        return new ArrayList<>(currencyDataList);
    }

    public String getMessageAttribute() {
        return messageAttribute;
    }

    public boolean isEmpty() {
        return currencyDataList.isEmpty();
    }

    public boolean isEmptyMessage() {
        return Objects.isNull(messageAttribute) || messageAttribute.equals("");
    }

}
