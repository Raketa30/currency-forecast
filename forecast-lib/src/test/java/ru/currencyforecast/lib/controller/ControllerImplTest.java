package ru.currencyforecast.lib.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.domain.ForecastRequest;
import ru.currencyforecast.lib.domain.message.DataMessage;
import ru.currencyforecast.lib.domain.response.Response;
import ru.currencyforecast.lib.domain.response.ResponseImpl;
import ru.currencyforecast.lib.domain.response.ResponseType;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.lib.service.ServiceImpl;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ControllerImplTest {
    private ServiceImpl service;
    private DataModel dataModel;
    private ControllerImpl controller;

    @BeforeEach
    void setUp() {
        this.service = mock(ServiceImpl.class);
        this.dataModel = new DataModel();
        this.controller = new ControllerImpl(dataModel, service);
    }

    @Test
    void shouldAddMessageResponseToDataModel() {
        //given
        String message = "Test Message";
        //when
        controller.addMessage(message);
        //then
        assertThat(dataModel.getResponse().getMessage().getData()).isEqualTo(message);
    }

    @Test
    void shouldAddDataResponseToDataModel() {
        //given
        ForecastRequest request = ForecastRequest.builder()
                .algorithm("mistic")
                .currencyList(Collections.singletonList("USD"))
                .output("list")
                .periodOrDate("week")
                .build();

        CurrencyData currencyData = CurrencyData.builder()
                .nominal(1)
                .data(LocalDate.now())
                .curs(123.1)
                .cdx("USD")
                .build();
        Response response = new ResponseImpl<>(ResponseType.DATA, new DataMessage(Collections.singletonList(currencyData)));
        //when
        when(service.getForecastResponse(request)).thenReturn(response);
        controller.execute(request);
        //then
        assertThat(dataModel.getResponse()).isEqualTo(response);
    }
}