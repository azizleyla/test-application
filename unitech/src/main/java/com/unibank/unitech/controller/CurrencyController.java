package com.unibank.unitech.controller;

import com.unibank.unitech.dto.response.BaseResponseDto;
import com.unibank.unitech.dto.response.CurrencyResponse;
import com.unibank.unitech.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/currency")
@RequiredArgsConstructor
@Validated
@EnableCaching
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public BaseResponseDto<CurrencyResponse> getCurrencyRate(@RequestParam String currencyPair) {
        CurrencyResponse currencyRate = currencyService.getCurrencyRate(currencyPair);
        return BaseResponseDto.success(currencyRate);
    }

}
