package com.aninfo.model;

import javax.persistence.Entity;

@Entity
public class Withdraw extends Transaction {
    public Withdraw() {
    }

    public Withdraw(Long cbu,Double amount) {
        this.cbu = cbu;
        this.amount = amount;
        this.type = TransactionType.WITHDRAW;
    }

    @Override
    public Double getAmountAfterDeletion() {
        return this.amount;
    }
}
