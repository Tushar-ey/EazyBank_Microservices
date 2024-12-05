package com.eazybytes.accounts.repository;

import com.eazybytes.accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Accounts,Long> {

    Optional<Accounts> findByCustomerId(Long customerId);

    /*

    if we want to change data in the database with custom derived method we need to add @Modifying and @transactional
     like here we used deleteByCustomerId
     */

    @Modifying
    @Transactional
    void deleteByCustomerId(Long customerId);
}
