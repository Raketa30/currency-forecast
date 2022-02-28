package ru.currencyforecast.app.domain;

import ru.currencyforecast.app.util.DateTimeUtil;

import java.time.LocalDate;

public class CurrencyData {
    private final LocalDate localDate;
    private final Double cost;
    private final String title;

    public CurrencyData(LocalDate localDate, Double cost, String title) {
        this.localDate = localDate;
        this.cost = cost;
        this.title = title;
    }

    private CurrencyData(DataBuilder builder) {
        this.localDate = builder.localDate;
        this.cost = builder.cost;
        this.title = builder.title;
    }

    public static DataBuilder builder() {
        return new DataBuilder();
    }

    public Double getCost() {
        return cost;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return String.format("%s - %.2f", DateTimeUtil.getFormattedWeekdayAndDate(localDate), cost);
    }

    public static class DataBuilder {
        private String title;
        private LocalDate localDate;
        private Double cost;

        public CurrencyData build() {
            return new CurrencyData(this);
        }

        public DataBuilder setCost(Double cost) {
            this.cost = cost;
            return this;
        }

        public DataBuilder setDate(LocalDate localDate) {
            this.localDate = localDate;
            return this;
        }

        public DataBuilder setTitle(String title) {
            this.title = title;
            return this;
        }
    }
}
