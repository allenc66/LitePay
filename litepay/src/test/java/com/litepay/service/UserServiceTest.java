package com.litepay.service;

import com.litepay.model.User;
import com.litepay.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepo;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepo = mock(UserRepository.class);
        userService = new UserService(userRepo);
    }

    @Test
    void testRegisterUser_success() {
        User user = new User("merchant1", "securepass123", "merchant1@example.com");

        when(userRepo.save(any(User.class))).thenReturn(user);

        User result = userService.registerUser(user);

        assertNotNull(result);
        assertEquals("merchant1", result.getUsername());
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void testFindByUsername_success() {
        User user = new User("merchant1", "securepass123", "merchant1@example.com");

        when(userRepo.findByUsername("merchant1")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername("merchant1");

        assertTrue(result.isPresent());
        assertEquals("merchant1", result.get().getUsername());
        verify(userRepo, times(1)).findByUsername("merchant1");
    }

    @Test
    void testGetUserById_success() {
        UUID userId = UUID.randomUUID();
        User user = new User("merchant1", "securepass123", "merchant1@example.com");
        user.setId(userId);

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepo, times(1)).findById(userId);
    }

    @Test
    void testGetUserById_notFound() {
        UUID userId = UUID.randomUUID();

        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                userService.getUserById(userId)
        );

        assertEquals("User not found", ex.getMessage());
        verify(userRepo, times(1)).findById(userId);
    }
}

