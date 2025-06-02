package com.litepay.controller;

import com.litepay.model.Transaction;
import com.litepay.model.User;
import com.litepay.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TransactionControllerTest {

    private TransactionService transactionService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        transactionService = Mockito.mock(TransactionService.class);
        TransactionController controller = new TransactionController(transactionService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetTransaction() throws Exception {
        UUID txnId = UUID.randomUUID();
        User user = new User("merchant1", "pass123", "merchant1@example.com");
        user.setId(UUID.randomUUID());

        Transaction txn = new Transaction();
        txn.setId(txnId);
        txn.setAmount(100.0);
        txn.setUser(user);

        when(transactionService.getTransaction(txnId)).thenReturn(txn);

        mockMvc.perform(get("/api/transactions/" + txnId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(txnId.toString()))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.user.username").value("merchant1"))
                .andExpect(jsonPath("$.user.email").value("merchant1@example.com"));
    }

    @Test
    void testCreateTransaction() throws Exception {
        UUID userId = UUID.randomUUID();
        User user = new User("merchant2", "pass456", "merchant2@example.com");
        user.setId(userId);

        Transaction txn = new Transaction();
        txn.setAmount(200.0);
        txn.setUser(user);

        when(transactionService.createTransaction(eq(userId), eq(200.0))).thenReturn(txn);

        mockMvc.perform(post("/api/transactions")
                        .param("userId", userId.toString())
                        .param("amount", "200.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(200.0))
                .andExpect(jsonPath("$.user.username").value("merchant2"))
                .andExpect(jsonPath("$.user.email").value("merchant2@example.com"));
    }
}
