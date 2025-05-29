package com.taskmanager.taskapp.service.impl;

import com.taskmanager.taskapp.config.RabbitMQConfig;
import com.taskmanager.taskapp.model.Task;
import com.taskmanager.taskapp.repository.TaskRepository;
import com.taskmanager.taskapp.service.TaskService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final RabbitTemplate rabbitTemplate;

    public TaskServiceImpl(TaskRepository taskRepository, RabbitTemplate rabbitTemplate) {
        this.taskRepository = taskRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    @CacheEvict(value = {"allTasks", "pendingTasks"}, key = "#task.userId")
    public Task addTask(Task task) {
        Task savedTask = taskRepository.save(task);

        // ✅ Send task message to RabbitMQ
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.TASK_EXCHANGE,
                RabbitMQConfig.TASK_ROUTING_KEY,
                savedTask
        );

        return savedTask;
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
            evictUserTaskCache(task.getUserId());
        });
    }

    @Override
    @Async
    public void processOverdueTasks() {
        List<Task> overdueTasks = taskRepository.findAll().stream()
            .filter(task -> !task.isDeleted() && task.getTargetDate().isBefore(LocalDateTime.now()))
            .collect(Collectors.toList());

        for (Task task : overdueTasks) {
            System.out.println("⚠️ Overdue task detected: " + task.getTitle());
        }
    }

    @Override
    @CacheEvict(value = {"allTasks", "pendingTasks"}, key = "#userId")
    public void evictUserTaskCache(Long userId) {
        // This method triggers cache eviction manually
    }
}
