package ru.currencyforecast.lib.domain.response;

import com.github.sh0nk.matplotlib4j.Plot;

public class GraphMessageImpl implements Message {
    private final Plot plot;

    public GraphMessageImpl(Plot plot) {
        this.plot = plot;
    }

    @Override
    public Plot getMessageData() {
        return plot;
    }
}
