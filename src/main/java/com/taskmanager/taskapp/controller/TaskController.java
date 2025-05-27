package com.taskmanager.taskapp.controller;

import com.taskmanager.taskapp.model.Task;
import com.taskmanager.taskapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        Task created = taskService.addTask(task);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAll(@RequestParam Long userId) {
        return ResponseEntity.ok(taskService.getAllUserTasks(userId));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Task>> getPending(@RequestParam Long userId) {
        return ResponseEntity.ok(taskService.getPendingTasks(userId));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
