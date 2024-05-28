package com.unibank.unitech.repository;

import com.unibank.unitech.entity.HttpLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HttpLogRepository extends JpaRepository<HttpLog, Long> {
}
