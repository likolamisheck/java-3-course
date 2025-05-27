package com.taskmanager.taskapp.service.impl;

import com.taskmanager.taskapp.model.User;
import com.taskmanager.taskapp.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserServiceImpl implements UserService {

    private final Map<String, User> users = new HashMap<>();
    private final AtomicLong userIdGenerator = new AtomicLong();

    @Override
    public User register(User user) {
        user.setId(userIdGenerator.incrementAndGet());
        users.put(user.getUsername(), user);
        return user;
    }

    @Override
    public User login(String username, String password) {
        User user = users.get(username);
        return (user != null && user.getPassword().equals(password)) ? user : null;
    }
}
