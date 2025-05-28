package com.taskmanager.taskapp.service.impl;

import com.taskmanager.taskapp.model.Task;
import com.taskmanager.taskapp.repository.TaskRepository;
import com.taskmanager.taskapp.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllUserTasks(Long userId) {
        return taskRepository.findByUserIdAndDeletedFalse(userId);
    }

    @Override
    public List<Task> getPendingTasks(Long userId) {
        return taskRepository.findByUserIdAndCompletedFalseAndDeletedFalse(userId);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.findById(taskId).ifPresent(task -> {
            task.setDeleted(true);
            taskRepository.save(task);
        });
    }
}
