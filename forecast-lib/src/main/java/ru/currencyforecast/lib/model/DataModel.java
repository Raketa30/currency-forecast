package ru.currencyforecast.lib.model;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.currencyforecast.lib.domain.response.Response;

import java.util.Objects;

@Slf4j
@NoArgsConstructor
public class DataModel {
    private ThreadLocal<Response> responseData;

    public Response getResponse() {
        Response response = responseData.get();
        responseData.remove();
        return response;
    }

    public boolean isNotEmpty() {
        return !Objects.isNull(responseData);
    }

    public void setResponseData(Response response) {
        this.responseData = new ThreadLocal<>();
        responseData.set(response);
        log.debug("DataModel setResponseData response: {}", response.toString());
    }
}
