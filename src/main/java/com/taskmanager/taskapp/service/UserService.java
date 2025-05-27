package com.taskmanager.taskapp.service;

import com.taskmanager.taskapp.model.User;

public interface UserService {
    User register(User user);
    User login(String username, String password);
}
