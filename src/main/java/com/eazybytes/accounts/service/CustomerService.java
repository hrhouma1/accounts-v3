package com.eazybytes.accounts.service;

import com.eazybytes.accounts.model.Accounts;
import com.eazybytes.accounts.model.Customer;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    public List<Customer> getAllCustomer() {
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);
        return customers;
    }

    public Customer getCustomerById(int id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client non trouvé"));
    }

    public Customer saveCustomer(Customer customer) {
        customer.setCreateDt(LocalDate.now());
        return customerRepository.save(customer);
    }

    public String updateCustomer(int id, Customer updateCustomer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client non trouvé"));
        updateCustomer.setCustomerId(id);
        updateCustomer.setCreateDt(LocalDate.now());
        customerRepository.save(updateCustomer);
        return "Mise à jour réussie !";
    }

    public String deleteCustomer(int id) {
        List<Accounts> accounts = accountsRepository.findByCustomerId(id);
        if (!accounts.isEmpty()) {
            return "Échec : Le client a des comptes associés";
        }
        customerRepository.deleteById(id);
        return "Suppression réussie";
    }

    public boolean customerExist(int id) {
        return customerRepository.existsById(id);
    }
}