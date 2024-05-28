package com.unibank.unitech.service.impl;

import com.unibank.unitech.dto.response.CurrencyResponse;
import com.unibank.unitech.service.CurrencyRateCache;
import com.unibank.unitech.service.CurrencyService;
import com.unibank.unitech.service.ThirdPartyCurrencyService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRateCache cache;
    private final ThirdPartyCurrencyService thirdPartyService;
    public CurrencyResponse getCurrencyRate(String currencyPair) {
        BigDecimal rate = cache.getCurrencyRate(currencyPair);
        if (rate == null) {
            try {
                rate = thirdPartyService.fetchCurrencyRate(currencyPair);
                cache.setCurrencyRate(currencyPair, rate);
            } catch (Exception e) {
                log.error("Failed to fetch currency rate for {}", currencyPair, e);

            }
        }
        return CurrencyResponse.builder()
                .rate(rate)
                .build();
    }

    @Scheduled(cron = "0 0 * * * *")
    public void clearCache() {
        cache.clear();
        log.info("Currency cache cleared.");
    }
}

