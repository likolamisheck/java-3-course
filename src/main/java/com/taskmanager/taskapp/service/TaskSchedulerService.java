package com.taskmanager.taskapp.service;

import com.taskmanager.taskapp.service.TaskService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TaskSchedulerService {

    private final TaskService taskService;

    public TaskSchedulerService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Scheduled(fixedRate = 60000) // runs every 60 seconds
    public void checkOverdueTasks() {
        taskService.processOverdueTasks();
    }
}
