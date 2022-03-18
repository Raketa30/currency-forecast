package ru.currencyforecast.lib.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.domain.ForecastRequest;
import ru.currencyforecast.lib.domain.response.Response;
import ru.currencyforecast.lib.domain.response.ResponseType;
import ru.currencyforecast.lib.repository.Repository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static ru.currencyforecast.lib.common.Constant.*;

@ExtendWith(MockitoExtension.class)
class ServiceImplTest {
    @Mock
    private Repository repository;
    @Mock
    private ForecastService forecastService;
    @Mock
    private TrendService trendService;
    @InjectMocks
    private ServiceImpl service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldReturnDataTypeResponseWithCorrectRequest() {
        //given
        ForecastRequest request = ForecastRequest.builder()
                .algorithm(ALG_MISTIC)
                .currencyList(Collections.singletonList(CURRENCY_USD))
                .output(OUTPUT_LIST)
                .periodOrDate(FORECAST_DEPTH_WEEK)
                .build();
        CurrencyData cur1 = new CurrencyData(1, LocalDate.now(), 12.2, "Доллар сша");
        CurrencyData cur2 = new CurrencyData(1, LocalDate.now(), 12.2, "Доллар сша");
        //when
        when(forecastService.setAlgorithm(request.getAlgorithm())).thenReturn(true);
        when(forecastService.getAlgBaseIndex()).thenReturn(ALG_MISTIC_BASE);
        when(repository.getDataByCdxAndLimitByALgBaseIndex(request.getCurrencyList().get(0), ALG_MISTIC_BASE)).thenReturn(List.of(cur1, cur2));
        Response response = service.getForecastResponse(request);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getType()).isEqualTo(ResponseType.DATA);
        verify(repository, times(1)).getDataByCdxAndLimitByALgBaseIndex(request.getCurrencyList().get(0), ALG_MISTIC_BASE);
    }

    @Test
    void shouldReturnMessageTypeResponseWithWrongAlg() {
        //given
        ForecastRequest request = ForecastRequest.builder()
                .algorithm("wrong")
                .currencyList(Collections.singletonList(CURRENCY_USD))
                .output(OUTPUT_LIST)
                .periodOrDate(FORECAST_DEPTH_WEEK)
                .build();

        when(forecastService.setAlgorithm(request.getAlgorithm())).thenReturn(false);
        //when
        Response response = service.getForecastResponse(request);
        assertThat(response).isNotNull();
        assertThat(response.getType()).isEqualTo(ResponseType.TEXT);
        //then
        verify(repository, times(0)).getDataByCdxAndLimitByALgBaseIndex(request.getCurrencyList().get(0), ALG_MISTIC_BASE);
    }

    @Test
    void shouldReturnMessageTypeResponseWithWrongCurrency() {
        //given
        ForecastRequest request = ForecastRequest.builder()
                .algorithm(ALG_MISTIC)
                .currencyList(Collections.singletonList("wrong"))
                .output(OUTPUT_LIST)
                .periodOrDate(FORECAST_DEPTH_WEEK)
                .build();

        when(forecastService.setAlgorithm(request.getAlgorithm())).thenReturn(true);
        when(forecastService.getAlgBaseIndex()).thenReturn(ALG_MISTIC_BASE);
        when(repository.getDataByCdxAndLimitByALgBaseIndex(request.getCurrencyList().get(0), ALG_MISTIC_BASE)).thenReturn(Collections.emptyList());
        //when
        Response response = service.getForecastResponse(request);
        assertThat(response).isNotNull();
        assertThat(response.getType()).isEqualTo(ResponseType.TEXT);
        //then
        verify(repository, times(1)).getDataByCdxAndLimitByALgBaseIndex(request.getCurrencyList().get(0), ALG_MISTIC_BASE);
    }

}