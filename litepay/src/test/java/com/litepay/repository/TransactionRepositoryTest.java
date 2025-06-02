package com.litepay.repository;

import com.litepay.model.Transaction;
import com.litepay.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindTransaction() {
        User user = new User("merchant3", "abc123", "merchant3@example.com");
        User savedUser = userRepository.save(user);

        Transaction tx = new Transaction(99.99, "Success", LocalDateTime.now(), savedUser);
        Transaction savedTx = transactionRepository.save(tx);

        Optional<Transaction> result = transactionRepository.findById(savedTx.getId());

        assertTrue(result.isPresent());
        assertEquals(99.99, result.get().getAmount());
        assertEquals("merchant3", result.get().getUser().getUsername());
    }
}

