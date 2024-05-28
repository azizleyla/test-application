package com.unibank.unitech.service.impl;

import com.unibank.unitech.dto.request.AccountCreateRequestDto;
import com.unibank.unitech.dto.response.AccountResponseDto;
import com.unibank.unitech.entity.Account;
import com.unibank.unitech.enums.AccountStatutes;
import com.unibank.unitech.exception.UserNotFoundWithGivenPin;
import com.unibank.unitech.repository.AccountRepository;
import com.unibank.unitech.repository.UserRepository;
import com.unibank.unitech.service.AccountService;
import com.unibank.unitech.service.JWTService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final JWTService jwtService;

    @Transactional
    @Override
    public AccountResponseDto createAccount(AccountCreateRequestDto accountCreateRequestDto, String authentication) {

        var tokenPrincipalDto = jwtService.getUserPrincipal(authentication);
        var user = userRepository.findUserInfoByPinIgnoreCase(tokenPrincipalDto.sub())
                .stream().findFirst().orElseThrow(UserNotFoundWithGivenPin::new);

        Account account = Account.builder()
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .accountNumber(accountCreateRequestDto.getAccountNumber())
                .balance(accountCreateRequestDto.getBalance())
                .userId(user)
                .status(AccountStatutes.DEACTIVATED)
                .build();

        return fromAccountEntityToAccountResponse(accountRepository.save(account));

    }

    @Override
    public List<AccountResponseDto> getAllAccounts(String authentication) {

        var tokenPrincipalDto = jwtService.getUserPrincipal(authentication);
        var user = userRepository.findUserInfoByPinIgnoreCase(tokenPrincipalDto.sub())
                .stream().findFirst().orElseThrow(UserNotFoundWithGivenPin::new);

        return accountRepository
                .findAllByUserId(user)
                .stream()
                .filter(account -> account.getStatus().equals(AccountStatutes.ACTIVE))
                .map(this::fromAccountEntityToAccountResponse)
                .toList();

    }

    private AccountResponseDto fromAccountEntityToAccountResponse(Account account) {
        return AccountResponseDto.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .status(account.getStatus())
                .build();
    }

}
