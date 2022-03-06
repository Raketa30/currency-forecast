package ru.currencyforecast.app.domain;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import ru.currencyforecast.app.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.Objects;


public class CurrencyData {
    @CsvBindByName
    private Integer nominal;

    @CsvBindByName
    @CsvDate("dd.MM.yyyy")
    private LocalDate data;

    @CsvBindByName(locale = "ru-RU")
    private Double curs;

    @CsvBindByName
    private String cdx;

    public CurrencyData() {
    }

    public CurrencyData(Integer nominal, LocalDate data, Double curs, String cdx) {
        this.nominal = nominal;
        this.data = data;
        this.curs = curs;
        this.cdx = cdx;
    }

    private CurrencyData(DataBuilder builder) {
        this.nominal = builder.nominal;
        this.data = builder.localDate;
        this.curs = builder.cost;
        this.cdx = builder.title;
    }

    public static DataBuilder builder() {
        return new DataBuilder();
    }

    public String getCdx() {
        return cdx;
    }

    public void setCdx(String cdx) {
        this.cdx = cdx;
    }

    public Double getCurs() {
        return curs;
    }

    public void setCurs(Double curs) {
        this.curs = curs;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getNominal() {
        return nominal;
    }

    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nominal, data, curs, cdx);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyData)) return false;
        CurrencyData that = (CurrencyData) o;
        return nominal.equals(that.nominal) && data.equals(that.data) && curs.equals(that.curs) && cdx.equals(that.cdx);
    }

    @Override
    public String toString() {
        return String.format("%s - %.2f %s", DateTimeUtil.getFormattedWeekdayAndDate(data), curs, cdx);
    }

    public static class DataBuilder {
        private Integer nominal;
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

        public DataBuilder setNominal(Integer nominal) {
            this.nominal = nominal;
            return this;
        }

        public DataBuilder setTitle(String title) {
            this.title = title;
            return this;
        }
    }


}
