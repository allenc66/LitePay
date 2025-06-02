package com.litepay.service;

import com.litepay.model.Transaction;
import com.litepay.model.User;
import com.litepay.repository.TransactionRepository;
import com.litepay.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
public class TransactionServiceTest {

    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        userRepository = mock(UserRepository.class);
        transactionService = new TransactionService(transactionRepository, userRepository);
    }

    @Test
    void testCreateTransaction_success() {
        UUID userId = UUID.randomUUID();
        User user = new User("merchant1", "1234", "merchant1@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArgument(0));

        Transaction tx = transactionService.createTransaction(userId, 100.0);

        assertEquals(100.0, tx.getAmount());
        assertEquals("Success", tx.getStatus());
        assertNotNull(tx.getTimestamp());
        assertEquals(user, tx.getUser());
    }

    @Test
    void testCreateTransaction_userNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                transactionService.createTransaction(userId, 50.0)
        );

        assertEquals("User not found with ID: " + userId, ex.getMessage());
    }

    @Test
    void testGetTransaction_success() {
        UUID txId = UUID.randomUUID();
        Transaction mockTx = new Transaction();
        mockTx.setId(txId);
        mockTx.setAmount(200.0);
        mockTx.setTimestamp(LocalDateTime.now());
        mockTx.setStatus("SUCCESS");

        when(transactionRepository.findById(txId)).thenReturn(Optional.of(mockTx));

        Transaction result = transactionService.getTransaction(txId);
        assertEquals(200.0, result.getAmount());
    }

    @Test
    void testGetTransaction_notFound() {
        UUID txId = UUID.randomUUID();
        when(transactionRepository.findById(txId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                transactionService.getTransaction(txId)
        );

        assertEquals("Transaction not found with ID: " + txId, ex.getMessage());
    }
}
