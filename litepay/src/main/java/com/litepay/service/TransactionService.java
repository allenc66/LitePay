package com.litepay.service;

import com.litepay.model.Transaction;
import com.litepay.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository repo;

    public TransactionService(TransactionRepository repo) {
        this.repo = repo;
    }

    public Transaction createTransaction(String merchantId, Double amount) {
        Transaction tx = new Transaction();
        tx.setMerchantId(merchantId);
        tx.setAmount(amount);
        tx.setStatus("Success"); // simulate always success for now
        tx.setTimestamp(LocalDateTime.now());
        return repo.save(tx);
    }

    public Transaction getTransaction(UUID id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }
}
