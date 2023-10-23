package com.aninfo.model;

import javax.persistence.Entity;

@Entity
public class Deposit extends Transaction {

    public Deposit() {
    }

    public Deposit(Long cbu, Double amount) {
        this.cbu = cbu;
        this.amount = amount;
        this.type = TransactionType.DEPOSIT;
    }

    @Override
    public Double getAmountAfterDeletion() {
        return -this.amount;
    }

}
