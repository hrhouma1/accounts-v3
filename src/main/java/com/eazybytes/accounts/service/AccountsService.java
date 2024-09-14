package com.eazybytes.accounts.service;

import com.eazybytes.accounts.model.Accounts;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountsService {

    private static final Logger logger = LoggerFactory.getLogger(AccountsService.class);

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<Accounts> getAllAccounts() {
        // 🟢 Fetching all accounts
        logger.info("Fetching all accounts");
        List<Accounts> allAccounts = new ArrayList<>();
        accountsRepository.findAll().forEach(allAccounts::add);
        // 🔵 Number of accounts fetched
        logger.debug("Number of accounts fetched: {}", allAccounts.size());
        return allAccounts;
    }

    public Accounts getAccountById(long id) {
        // 🟢 Fetching account by ID
        logger.info("Fetching account with ID: {}", id);
        return accountsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Compte non trouvé"));
    }

    public Accounts saveAccount(Accounts accounts) {
        // 🔵 Attempting to save account
        logger.debug("Attempting to save account: {}", accounts);
        int customerId = accounts.getCustomerId();
        if (customerRepository.existsById(customerId)) {
            accounts.setCreateDt(LocalDate.now());
            Accounts savedAccount = accountsRepository.save(accounts);
            // 🟢 Account saved successfully
            logger.info("Account saved successfully: {}", savedAccount);
            return savedAccount;
        } else {
            // 🟠 Failed to save account, customer not found
            logger.warn("Failed to save account, customer not found: {}", customerId);
            throw new EntityNotFoundException("Client non trouvé !");
        }
    }

    public Accounts updateAccount(long id, Accounts updateAccounts) {
        // 🟢 Updating account
        logger.info("Updating account with ID: {}", id);
        Accounts existingAccount = accountsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Compte non trouvé"));
        updateAccounts.setAccountNumber(id);
        updateAccounts.setCreateDt(LocalDate.now());
        Accounts updatedAccount = accountsRepository.save(updateAccounts);
        // 🟢 Account updated successfully
        logger.info("Account updated successfully: {}", updatedAccount);
        return updatedAccount;
    }

    public String deleteAccount(Long id) {
        // 🟢 Deleting account
        logger.info("Deleting account with ID: {}", id);
        if (!accountsRepository.existsById(id)) {
            // 🟠 Account not found
            logger.warn("Account not found: {}", id);
            return "Account not found!";
        }
        accountsRepository.deleteById(id);
        // 🟢 Account deleted successfully
        logger.info("Account deleted successfully: {}", id);
        return "Account deleted successfully!";
    }

    @Transactional
    public List<String> saveAll(List<Accounts> accountsList) {
        // 🟢 Saving multiple accounts
        logger.info("Saving multiple accounts");
        List<String> responses = new ArrayList<>();
        for (Accounts account : accountsList) {
            try {
                accountsRepository.save(account);
                responses.add("Compte enregistré avec succès : " + account.getAccountNumber());
                // 🟢 Account saved successfully
                logger.info("Account saved successfully: {}", account.getAccountNumber());
            } catch (Exception e) {
                responses.add("Échec d'enregistrement pour le compte : " + account.getAccountNumber() + " - Erreur : " + e.getMessage());
                // 🔴 Failed to save account
                logger.error("Failed to save account: {} - Error: {}", account.getAccountNumber(), e.getMessage());
            }
        }
        return responses;
    }

    @Transactional
    public String deleteAllAccounts() {
        // 🟢 Deleting all accounts
        logger.info("Deleting all accounts");
        try {
            accountsRepository.deleteAll();
            // 🟢 All accounts deleted successfully
            logger.info("All accounts deleted successfully");
            return "Tous les comptes ont été supprimés avec succès";
        } catch (Exception e) {
            // 🔴 Error deleting all accounts
            logger.error("Error deleting all accounts: {}", e.getMessage());
            return "Erreur lors de la suppression des comptes : " + e.getMessage();
        }
    }

    @Transactional
    public String deleteAllByIds(List<Long> accountIds) {
        // 🟢 Deleting accounts by IDs
        logger.info("Deleting accounts by IDs: {}", accountIds);
        try {
            accountsRepository.deleteAllByAccountNumberIn(accountIds);
            // 🟢 Accounts deleted successfully
            logger.info("Accounts deleted successfully");
            return "Comptes supprimés avec succès";
        } catch (Exception e) {
            // 🔴 Error deleting accounts
            logger.error("Error deleting accounts: {}", e.getMessage());
            return "Erreur lors de la suppression des comptes : " + e.getMessage();
        }
    }

    @Transactional
    public String updateAccounts(List<Accounts> accountsList) {
        // 🟢 Updating multiple accounts
        logger.info("Updating multiple accounts");
        try {
            accountsList.forEach(accountsRepository::save);
            // 🟢 Accounts updated successfully
            logger.info("Accounts updated successfully");
            return "Comptes mis à jour avec succès";
        } catch (Exception e) {
            // 🔴 Error updating accounts
            logger.error("Error updating accounts: {}", e.getMessage());
            return "Erreur lors de la mise à jour des comptes : " + e.getMessage();
        }
    }

    public List<Accounts> findAllByIds(List<Long> accountIds) {
        // 🟢 Finding accounts by IDs
        logger.info("Finding accounts by IDs: {}", accountIds);
        List<Accounts> accounts = (List<Accounts>) accountsRepository.findAllById(accountIds);
        // 🔵 Number of accounts found
        logger.debug("Number of accounts found: {}", accounts.size());
        return accounts;
    }
}