package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.Account;
import com.aninfo.model.Deposit;
import com.aninfo.model.Transaction;
import com.aninfo.model.Withdraw;
import com.aninfo.repository.AccountRepository;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    private Transaction createTransaction(Transaction transaction){ transactionRepository.save(transaction);
        return transaction;
    }

    public Collection<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }


    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    public Collection<Transaction> findAllByCbu(Long cbu){
        return transactionRepository.findAllByCbu(cbu);
    }

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void deleteById(Long id) {

        Transaction transaction = transactionRepository.findTransactionById(id);
        Account account = accountRepository.findAccountByCbu(transaction.getCbu());

        Double new_balance = account.getBalance() + transaction.getAmountAfterDeletion();

        if (new_balance < 0) {
            throw new InvalidTransactionTypeException("Cannot delete transaction");
        }

        account.setBalance(new_balance);

        transactionRepository.deleteById(id);
    }

    public Deposit createDeposit(Deposit deposit){
        return (Deposit) createTransaction(deposit);
    }

    public Withdraw createWithdraw(Withdraw withdraw){
        return (Withdraw) createTransaction(withdraw);
    }

    @Transactional
    public Deposit deposit(Long cbu, Double amount) {

        if (amount <= 0) {
            throw new DepositNegativeSumException("Cannot deposit negative or zero sums");
        }

        if (amount >= 2000) {
            double promo = amount * 0.1;
            if (promo > 500) {
                promo = 500;
            }
            amount = amount + promo;
        }

        Account account = accountRepository.findAccountByCbu(cbu);
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        return createDeposit(new Deposit(cbu, amount));
    }

    @Transactional
    public Withdraw withdraw(Long cbu, Double sum) {
        Account account = accountRepository.findAccountByCbu(cbu);

        if (account.getBalance() < sum) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - sum);
        accountRepository.save(account);

        return createWithdraw(new Withdraw(cbu, sum));
    }

}
