package com.taskmanager.taskapp.service.impl;

import com.taskmanager.taskapp.model.User;
import com.taskmanager.taskapp.repository.UserRepository;
import com.taskmanager.taskapp.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(User user) {
        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.filter(u -> u.getPassword().equals(password)).orElse(null);
    }
}
