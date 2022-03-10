package ru.currencyforecast.lib.domain;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private List<String> currencyList;
    private String periodOrDate;
    private String algorithm;
    private String output;
}
