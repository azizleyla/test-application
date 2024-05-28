package com.unibank.unitech.repository;

import com.unibank.unitech.entity.Transaction;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

}
