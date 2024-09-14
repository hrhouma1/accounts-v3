package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.model.Accounts;
import com.eazybytes.accounts.service.AccountsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @Operation(summary = "Get all accounts")
    @GetMapping
    public ResponseEntity<List<Accounts>> getAllAccounts() {
        List<Accounts> accounts = accountsService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @Operation(summary = "Get account by ID")
    @GetMapping("/myAccount/{id}")
    public ResponseEntity<Accounts> getAccountById(@PathVariable("id") Long id) {
        Accounts account = accountsService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @Operation(summary = "Create a new account")
    @PostMapping("/newAccount")
    public ResponseEntity<Accounts> createAccount(@RequestBody Accounts accounts) {
        Accounts savedAccount = accountsService.saveAccount(accounts);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

    @Operation(summary = "Create multiple accounts")
    @PostMapping("/newAccounts")
    public ResponseEntity<List<String>> createMultipleAccounts(@RequestBody List<Accounts> accountsList) {
        List<String> responses = accountsService.saveAll(accountsList);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @Operation(summary = "Update an account by ID")
    @PutMapping("/update/{id}")
    public ResponseEntity<Accounts> updateAccount(@PathVariable("id") Long id, @RequestBody Accounts accounts) {
        Accounts updatedAccount = accountsService.updateAccount(id, accounts);
        return ResponseEntity.ok(updatedAccount);
    }

    @Operation(summary = "Update multiple accounts")
    @PutMapping("/updateAccounts")
    public ResponseEntity<String> updateMultipleAccounts(@RequestBody List<Accounts> accountsList) {
        String response = accountsService.updateAccounts(accountsList);
        return ResponseEntity.ok(response);
    }

 

    @Operation(summary = "Delete an account by ID")
    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") Long id) {
        String result = accountsService.deleteAccount(id);
        if ("Account not found!".equals(result)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }

    /*   public ResponseEntity<Void> deleteAccount(@PathVariable("id") Long id) {
        accountsService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    } */

    @Operation(summary = "Delete all accounts")
    @DeleteMapping("/deleteAllAccounts")
    public ResponseEntity<String> deleteAllAccounts() {
        String response = accountsService.deleteAllAccounts();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete multiple accounts by IDs")
    @DeleteMapping("/deleteAccounts")
    public ResponseEntity<String> deleteAccounts(@RequestBody List<Long> accountIds) {
        String response = accountsService.deleteAllByIds(accountIds);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Find accounts by IDs")
    @PostMapping("/findAccounts")
    public ResponseEntity<List<Accounts>> findAccounts(@RequestBody List<Long> accountIds) {
        List<Accounts> accounts = accountsService.findAllByIds(accountIds);
        return ResponseEntity.ok(accounts);
    }
}