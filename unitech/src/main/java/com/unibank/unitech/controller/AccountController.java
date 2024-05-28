package com.unibank.unitech.controller;

import com.unibank.unitech.dto.request.AccountCreateRequestDto;
import com.unibank.unitech.dto.response.AccountResponseDto;
import com.unibank.unitech.dto.response.BaseResponseDto;
import com.unibank.unitech.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.unibank.unitech.constants.GeneralConstants.AUTHORIZATION;

@RestController
@RequestMapping(value = "/account")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public BaseResponseDto<AccountResponseDto> createAccount(
            @RequestBody @Valid AccountCreateRequestDto accountCreateRequestDto,
            @RequestHeader(name = AUTHORIZATION) String authentication) {
        AccountResponseDto accountResponseDto = accountService.createAccount(accountCreateRequestDto, authentication);
        return BaseResponseDto.success(accountResponseDto);
    }

    @GetMapping
    public BaseResponseDto<List<AccountResponseDto>> getAllAccounts(
            @RequestHeader(name = AUTHORIZATION) String authentication) {
        List<AccountResponseDto> accountResponseDto = accountService.getAllAccounts(authentication);
        return BaseResponseDto.success(accountResponseDto);
    }

}
