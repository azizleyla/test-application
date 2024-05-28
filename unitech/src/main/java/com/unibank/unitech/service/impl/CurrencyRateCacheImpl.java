package com.unibank.unitech.service.impl;

import com.unibank.unitech.service.CurrencyRateCache;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class CurrencyRateCacheImpl implements CurrencyRateCache {

    private final Map<String, BigDecimal> cache = new ConcurrentHashMap<>();

    @Override
    public BigDecimal getCurrencyRate(String currencyPair) {
        return cache.get(currencyPair);
    }

    @Override
    public void setCurrencyRate(String currencyPair, BigDecimal rate) {
        cache.put(currencyPair, rate);
    }

    @Override
    public void clear() {
        cache.clear();
    }

}
