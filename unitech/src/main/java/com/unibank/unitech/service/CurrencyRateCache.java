package com.unibank.unitech.service;

import java.math.BigDecimal;

public interface CurrencyRateCache {

    BigDecimal getCurrencyRate(String currencyPair);

    void setCurrencyRate(String currencyPair, BigDecimal rate);

    void clear();

}