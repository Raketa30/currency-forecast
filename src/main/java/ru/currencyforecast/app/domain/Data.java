package ru.currencyforecast.app.domain;

import ru.currencyforecast.app.util.DateTimeUtil;

import java.time.LocalDate;

public class Data {
    private final LocalDate localDate;
    private final Double cost;

    public Data(LocalDate localDate, Double cost) {
        this.localDate = localDate;
        this.cost = cost;
    }

    private Data(DataBuilder builder) {
        this.localDate = builder.localDate;
        this.cost = builder.cost;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public Double getCost() {
        return cost;
    }

    public static DataBuilder builder() {
        return new DataBuilder();
    }

    public static class DataBuilder {
        private LocalDate localDate;
        private Double cost;

        public Data build() {
            return new Data(this);
        }

        public DataBuilder setCost(Double cost) {
            this.cost = cost;
            return this;
        }

        public DataBuilder setDate(LocalDate localDate) {
            this.localDate = localDate;
            return this;
        }
    }

    @Override
    public String toString() {
        return String.format("%s - %.2f", DateTimeUtil.getFormattedWeekdayAndDate(localDate), cost);
    }
}
