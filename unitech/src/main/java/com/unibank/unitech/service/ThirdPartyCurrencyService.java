package com.unibank.unitech.service;

import java.math.BigDecimal;

public interface ThirdPartyCurrencyService {

    BigDecimal fetchCurrencyRate(String currencyPair);

}
