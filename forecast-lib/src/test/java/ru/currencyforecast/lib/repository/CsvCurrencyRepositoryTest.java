package ru.currencyforecast.lib.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.currencyforecast.lib.common.Constant.ALG_MISTIC_BASE;
import static ru.currencyforecast.lib.common.Constant.CURRENCY_EUR;

@ExtendWith(MockitoExtension.class)
class CsvCurrencyRepositoryTest {
    private CsvCurrencyRepository repository;

    @BeforeEach
    void setUp() {
        repository = new CsvCurrencyRepository();
    }

    @Test
    void shouldReturnNotEmptyDataFromRepositoryWithCorrectCdx() {
        //when
        List<CurrencyData> allDataByCdx = repository.getAllDataByCdx(CURRENCY_EUR);
        //then
        assertThat(allDataByCdx.size()).isPositive();
    }

    @Test
    void shouldReturnEmptyDataFromRepositoryWithWrongCdx() {
        //when
        List<CurrencyData> allDataByCdx = repository.getAllDataByCdx("wrong");
        //then
        assertThat(allDataByCdx.size()).isZero();
    }

    @Test
    void shouldReturnDataListWithIndexSizeWithCorrectCdx() {
        //when
        List<CurrencyData> dataList = repository.getDataByCdxAndLimitByALgBaseIndex(CURRENCY_EUR, ALG_MISTIC_BASE);
        //then
        assertThat(dataList.size()).isEqualTo(ALG_MISTIC_BASE);
    }

    @Test
    void shouldReturnEmptyDataListWithIndexSizeWithWrongCdx() {
        //when
        List<CurrencyData> dataList = repository.getDataByCdxAndLimitByALgBaseIndex("wrong", ALG_MISTIC_BASE);
        //then
        assertThat(dataList.size()).isZero();
    }
}