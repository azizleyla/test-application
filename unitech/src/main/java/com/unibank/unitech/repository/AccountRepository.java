package com.unibank.unitech.repository;

import com.unibank.unitech.entity.Account;
import com.unibank.unitech.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountNumber(String accountNumber);

    List<Account> findAllByUserId(User user);

}
