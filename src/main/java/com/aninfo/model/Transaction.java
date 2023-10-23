package com.aninfo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    protected Double amount;
    protected Long cbu;
    protected TransactionType type;

    public Long getCbu() {
        return cbu;
    }

    public void setAssociatedCbu(Long cbu) {
        this.cbu = cbu;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    @JsonIgnore
    public Double getAmountAfterDeletion() {
        return 0.0;
    };

}
