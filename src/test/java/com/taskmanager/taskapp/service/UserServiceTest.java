package com.taskmanager.taskapp.service;

import com.taskmanager.taskapp.model.User;
import com.taskmanager.taskapp.repository.UserRepository;
import com.taskmanager.taskapp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void testRegisterUser() {
        User user = new User();
        user.setUsername("alice");
        user.setPassword("pass123");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("alice");
        savedUser.setPassword("pass123");

        when(userRepository.save(user)).thenReturn(savedUser);

        User registered = userService.register(user);

        assertNotNull(registered.getId());
        assertEquals("alice", registered.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    public void testLoginSuccess() {
        User user = new User();
        user.setUsername("bob");
        user.setPassword("mypassword");

        when(userRepository.findByUsername("bob")).thenReturn(Optional.of(user));

        User loggedIn = userService.login("bob", "mypassword");

        assertNotNull(loggedIn);
        assertEquals("bob", loggedIn.getUsername());
    }

    @Test
    public void testLoginFailure() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        User loggedIn = userService.login("unknown", "wrong");
        assertNull(loggedIn);
    }
}

