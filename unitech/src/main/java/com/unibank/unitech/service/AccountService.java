package com.unibank.unitech.service;

import com.unibank.unitech.dto.request.AccountCreateRequestDto;
import com.unibank.unitech.dto.response.AccountResponseDto;
import java.util.List;

public interface AccountService {

    AccountResponseDto createAccount(AccountCreateRequestDto accountCreateRequestDto, String auth);

    List<AccountResponseDto> getAllAccounts(String authentication);

}
