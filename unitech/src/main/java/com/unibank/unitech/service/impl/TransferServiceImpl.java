package com.unibank.unitech.service.impl;

import com.unibank.unitech.constants.GeneralConstants;
import com.unibank.unitech.dto.request.TransferRequestDto;
import com.unibank.unitech.dto.response.TransferResponseDto;
import com.unibank.unitech.entity.Account;
import com.unibank.unitech.entity.Transaction;
import com.unibank.unitech.entity.User;
import com.unibank.unitech.enums.AccountStatutes;
import com.unibank.unitech.enums.TransactionStatuses;
import com.unibank.unitech.exception.InsufficientBalanceException;
import com.unibank.unitech.exception.MoneyReceiverAccountException;
import com.unibank.unitech.exception.MoneySenderAccountException;
import com.unibank.unitech.exception.SameAccountException;
import com.unibank.unitech.exception.UserNotFoundWithGivenPin;
import com.unibank.unitech.repository.AccountRepository;
import com.unibank.unitech.repository.TransactionRepository;
import com.unibank.unitech.repository.UserRepository;
import com.unibank.unitech.service.JWTService;
import com.unibank.unitech.service.TransferService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final JWTService jwtService;

    @Transactional
    @Override
    public TransferResponseDto doTransfer(TransferRequestDto transferRequest, String authentication) {

        var tokenPrincipalDto = jwtService.getUserPrincipal(authentication);
        var user = userRepository.findUserInfoByPinIgnoreCase(tokenPrincipalDto.sub())
                .stream().findFirst().orElseThrow(UserNotFoundWithGivenPin::new);

        // Göndərən Account mövcuddur və Active dir yoxlanması
        Account sender = accountRepository.findByAccountNumber(transferRequest.getFromAccountNumber());
        if (sender == null || !sender.getStatus().equals(AccountStatutes.ACTIVE)) {
            throw new MoneySenderAccountException();
        }

        // Göndəriləcək Account mövcuddur və Active dir yoxlanması
        Account receiver = accountRepository.findByAccountNumber(transferRequest.getToAccountNumber());
        if (receiver == null || !receiver.getStatus().equals(AccountStatutes.ACTIVE)) {
            throw new MoneyReceiverAccountException();
        }

        // Accountlar ferqlidir ya yox
        if (transferRequest.getFromAccountNumber().equals(transferRequest.getToAccountNumber())) {
            throw new SameAccountException();
        }

        // Göndərən Account hesabında kifayət məbləğ varmı
        BigDecimal transferAmount = transferRequest.getAmount();
        if (sender.getBalance().compareTo(transferAmount) < 0) {
            throw new InsufficientBalanceException();
        }

        // Transfer gerçəkləşməsi
        return transfer(sender, receiver, transferRequest.getAmount(), user);

    }

    public TransferResponseDto transfer(Account sender, Account receiver, BigDecimal amount, User user) {

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .fromAccount(sender.getAccountNumber())
                .toAccount(receiver.getAccountNumber())
                .status(TransactionStatuses.SUCCESS)
                .createdAt(LocalDateTime.now())
                .userId(user)
                .build();

        transactionRepository.save(transaction);

        return TransferResponseDto.builder()
                .success(true)
                .transactionId(transaction.getId())
                .message(GeneralConstants.SUCCESS)
                .build();

    }

}
