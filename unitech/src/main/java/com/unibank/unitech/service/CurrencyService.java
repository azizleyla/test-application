package com.unibank.unitech.service;

import com.unibank.unitech.dto.response.CurrencyResponse;

public interface CurrencyService {

    CurrencyResponse getCurrencyRate(String currencyPair);

    void clearCache();

}
