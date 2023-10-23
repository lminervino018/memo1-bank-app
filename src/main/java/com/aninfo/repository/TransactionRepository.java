package com.aninfo.repository;

import com.aninfo.model.Account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.aninfo.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    Collection<Transaction> findAllByCbu(Long cbu);

    Optional<Transaction> findById(Long id);
    Transaction findTransactionById(Long id);

    void deleteById(Long id);

    @Override
    List<Transaction> findAll();

}
