package com.unibank.unitech.repository;

import com.unibank.unitech.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserInfoByPinIgnoreCase(String pin);

}
