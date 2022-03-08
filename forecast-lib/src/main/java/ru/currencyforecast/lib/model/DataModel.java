package ru.currencyforecast.lib.model;

import ru.currencyforecast.lib.domain.Response;

import java.util.Objects;

public class DataModel {
    private ThreadLocal<Response> responseData;

    public DataModel() {

    }

    public Response getResponseData() {
        return responseData.get();
    }

    public void setResponseData(Response response) {
        this.responseData = new ThreadLocal<>();
        responseData.set(response);
    }

    public boolean isEmpty() {
        return Objects.isNull(responseData);
    }
}
