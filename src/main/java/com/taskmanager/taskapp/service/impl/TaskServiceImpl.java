package com.taskmanager.taskapp.service.impl;

import com.taskmanager.taskapp.model.Task;
import com.taskmanager.taskapp.repository.TaskRepository;
import com.taskmanager.taskapp.service.TaskService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @CacheEvict(value = {"allTasks", "pendingTasks"}, key = "#task.userId")
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    @Cacheable(value = "allTasks", key = "#userId")
    public List<Task> getAllUserTasks(Long userId) {
        return taskRepository.findByUserIdAndDeletedFalse(userId);
    }

    @Override
    @Cacheable(value = "pendingTasks", key = "#userId")
    public List<Task> getPendingTasks(Long userId) {
        return taskRepository.findByUserIdAndCompletedFalseAndDeletedFalse(userId);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.findById(taskId).ifPresent(task -> {
            task.setDeleted(true);
            taskRepository.save(task);
            // Evict cache for updated user tasks
            evictUserTaskCache(task.getUserId());
        });
    }

    @CacheEvict(value = {"allTasks", "pendingTasks"}, key = "#userId")
    public void evictUserTaskCache(Long userId) {
        // Method only exists to trigger @CacheEvict
    }
}
