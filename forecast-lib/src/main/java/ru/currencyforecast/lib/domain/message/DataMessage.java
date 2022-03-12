package ru.currencyforecast.lib.domain.message;

import lombok.AllArgsConstructor;
import lombok.ToString;
import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;

@AllArgsConstructor
@ToString
public class DataMessage implements AbstractMessage<List<CurrencyData>> {
    private final List<CurrencyData> currencyDataList;

    @Override
    public List<CurrencyData> getData() {
        return currencyDataList;
    }
}
