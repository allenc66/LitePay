package com.litepay.service;

import com.litepay.model.Transaction;
import com.litepay.repository.TransactionRepository;

public class TransactionService {

    private final TransactionRepository repo;

    public TransactionService(TransactionRepository repo) {
        this.repo = repo;
    }

    public Transaction createTransaction(String merchantId, Double amount) {
        Transaction tx = new Transaction();

    }
}
