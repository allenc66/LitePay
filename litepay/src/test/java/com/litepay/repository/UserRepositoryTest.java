package com.litepay.repository;

import com.litepay.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindById() {
        User user = new User("merchant1", "pass123", "merchant1@example.com");
        User saved = userRepository.save(user);

        assertNotNull(saved.getId());
        Optional<User> found = userRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("merchant1", found.get().getUsername());
    }

    @Test
    void testFindByUsername() {
        User user = new User("merchant2", "secret", "merchant2@example.com");
        userRepository.save(user);

        Optional<User> result = userRepository.findByUsername("merchant2");

        assertTrue(result.isPresent());
        assertEquals("merchant2@example.com", result.get().getEmail());
    }
}
