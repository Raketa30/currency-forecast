package ru.currencyforecast.lib.domain;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ForecastRequest {
    private List<String> currencyList;
    private String periodOrDate;
    private String algorithm;
    private String output;
}
