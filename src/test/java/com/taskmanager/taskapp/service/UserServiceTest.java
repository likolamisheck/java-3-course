package com.taskmanager.taskapp.service;

import com.taskmanager.taskapp.model.User;
import com.taskmanager.taskapp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    public void setup() {
        userService = new UserServiceImpl();
    }

    @Test
    public void testRegisterUser() {
        User user = new User(null, "alice", "pass123");
        User registered = userService.register(user);
        assertNotNull(registered.getId());
        assertEquals("alice", registered.getUsername());
    }

    @Test
    public void testLoginSuccess() {
        User user = new User(null, "bob", "mypassword");
        userService.register(user);

        User loggedIn = userService.login("bob", "mypassword");
        assertNotNull(loggedIn);
        assertEquals("bob", loggedIn.getUsername());
    }

    @Test
    public void testLoginFailure() {
        User loggedIn = userService.login("unknown", "wrong");
        assertNull(loggedIn);
    }
}
