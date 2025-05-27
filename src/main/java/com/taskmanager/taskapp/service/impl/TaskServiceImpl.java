package com.taskmanager.taskapp.service.impl;

import com.taskmanager.taskapp.model.Task;
import com.taskmanager.taskapp.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final Map<Long, Task> tasks = new HashMap<>();
    private final AtomicLong taskIdGenerator = new AtomicLong();

    @Override
    public Task addTask(Task task) {
        task.setId(taskIdGenerator.incrementAndGet());
        task.setCreatedAt(java.time.LocalDateTime.now());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public List<Task> getAllUserTasks(Long userId) {
        return tasks.values().stream()
                .filter(t -> t.getUserId().equals(userId) && !t.isDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getPendingTasks(Long userId) {
        return tasks.values().stream()
                .filter(t -> t.getUserId().equals(userId) && !t.isCompleted() && !t.isDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Long taskId) {
        Task task = tasks.get(taskId);
        if (task != null) {
            task.setDeleted(true);
        }
    }
}
