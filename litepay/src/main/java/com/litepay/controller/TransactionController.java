package com.litepay.controller;

import com.litepay.model.Transaction;
import com.litepay.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public Transaction create(@RequestParam UUID userId, @RequestParam Double amount) {
        return service.createTransaction(userId, amount);
    }

    @GetMapping("/{id}")
    public Transaction get(@PathVariable UUID id) {
        return service.getTransaction(id);
    }
}
