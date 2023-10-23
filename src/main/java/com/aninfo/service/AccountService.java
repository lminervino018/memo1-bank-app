package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Account;
import com.aninfo.model.Deposit;
import com.aninfo.model.Transaction;
import com.aninfo.model.Withdraw;
import com.aninfo.repository.AccountRepository;
import com.aninfo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Collection<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long cbu) {
        return accountRepository.findById(cbu);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public void deleteById(Long cbu) {

        Collection<Transaction> transactions = transactionService.findAllByCbu(cbu);

        for (Transaction transaction : transactions) {
            transactionService.deleteById(transaction.getId());
        }

        accountRepository.deleteById(cbu);
    }

    @Transactional
    public Account withdraw(Long cbu, Double amount) {

        Withdraw withdraw = transactionService.withdraw(cbu, amount);

        return accountRepository.findAccountByCbu(cbu);
    }

    @Transactional
    public Account deposit(Long cbu, Double sum) {

        Deposit deposit = transactionService.deposit(cbu, sum);

        return accountRepository.findAccountByCbu(cbu);
    }

}
