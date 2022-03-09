package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.util.ArrayList;
import java.util.List;

import static ru.currencyforecast.lib.common.Constant.ALG_ACTUAL_BASE;

public class ActualAlgorithmImpl implements Algorithm {
    @Override
    public List<CurrencyData> getForcastForPeriod(List<CurrencyData> dataFromRepository, int periodDaysIndex) {
        List<CurrencyData> processList = new ArrayList<>(dataFromRepository);
        List<CurrencyData> resultList = new ArrayList<>();
        String titleFormList = getDataTitleFormList(dataFromRepository);
        int currencyNominal = getCurrencyNominal(dataFromRepository);



        return resultList;

    }

    private String getDataTitleFormList(List<CurrencyData> dataFromRepository) {
        return dataFromRepository.get(0).getCdx();
    }

    private int getCurrencyNominal(List<CurrencyData> dataFromRepository) {
        return dataFromRepository.get(0).getNominal();
    }

    @Override
    public int getBaseDaysNumber() {
        return ALG_ACTUAL_BASE;
    }
}
