package com.eazybytes.accounts.repository;

import com.eazybytes.accounts.model.Accounts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AccountsRepository extends CrudRepository<Accounts, Long> {

    List<Accounts> findByCreateDtAfter(LocalDate date);

    @Query("SELECT a FROM Accounts a WHERE a.accountNumber BETWEEN :startId AND :endId")
    List<Accounts> findAccountsInRange(long startId, long endId);

    @Query("SELECT COUNT(a) FROM Accounts a WHERE a.accountType = :accountType")
    long countByAccountType(String accountType);

    List<Accounts> findAllByAccountNumberIn(List<Long> ids);

    void deleteAllByAccountNumberIn(List<Long> accountNumbers);

    List<Accounts> findByCustomerId(int customerId);
}