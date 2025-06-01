package com.litepay.service;

import com.litepay.model.Transaction;
import com.litepay.model.User;
import com.litepay.repository.TransactionRepository;
import com.litepay.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepo;
    private final UserRepository userRepo;

    public TransactionService(TransactionRepository transactionRepo, UserRepository userRepo) {
        this.transactionRepo = transactionRepo;
        this.userRepo = userRepo;
    }

    public Transaction createTransaction(UUID userId, Double amount) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Transaction tx = new Transaction();
        tx.setUser(user);
        tx.setAmount(amount);
        tx.setStatus("Success"); // simulate always success for now
        tx.setTimestamp(LocalDateTime.now());
        return transactionRepo.save(tx);
    }

    public Transaction getTransaction(UUID id) {
        return transactionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }
}
