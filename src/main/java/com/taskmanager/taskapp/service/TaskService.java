package com.taskmanager.taskapp.service;

import com.taskmanager.taskapp.model.Task;

import java.util.List;

public interface TaskService {
    Task addTask(Task task);
    List<Task> getAllUserTasks(Long userId);
    List<Task> getPendingTasks(Long userId);
    void deleteTask(Long taskId);

    // New method for scheduled async processing
    void processOverdueTasks();
}
