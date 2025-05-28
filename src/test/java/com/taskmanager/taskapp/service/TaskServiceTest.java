package com.taskmanager.taskapp.service;

import com.taskmanager.taskapp.model.Task;
import com.taskmanager.taskapp.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    private TaskService taskService;

    @BeforeEach
    public void setup() {
        taskService = new TaskServiceImpl();
    }

    @Test
    public void testAddTask() {
        Task task = new Task(null, 1L, "Test task", null, LocalDateTime.now().plusDays(1));
        Task created = taskService.addTask(task);

        assertNotNull(created.getId());
        assertEquals("Test task", created.getDescription());
        assertEquals(1L, created.getUserId());
    }

    @Test
    public void testGetAllUserTasks() {
        taskService.addTask(new Task(null, 1L, "T1", null, LocalDateTime.now()));
        taskService.addTask(new Task(null, 1L, "T2", null, LocalDateTime.now()));
        taskService.addTask(new Task(null, 2L, "Other user", null, LocalDateTime.now()));

        List<Task> tasks = taskService.getAllUserTasks(1L);
        assertEquals(2, tasks.size());
    }

    @Test
    public void testGetPendingTasks() {
        Task task1 = new Task(null, 1L, "Active", null, LocalDateTime.now());
        Task task2 = new Task(null, 1L, "Completed", null, LocalDateTime.now());
        task2.setCompleted(true);

        taskService.addTask(task1);
        taskService.addTask(task2);

        List<Task> pending = taskService.getPendingTasks(1L);
        assertEquals(1, pending.size());
        assertEquals("Active", pending.get(0).getDescription());
    }

    @Test
    public void testDeleteTask() {
        Task task = taskService.addTask(new Task(null, 1L, "To delete", null, LocalDateTime.now()));
        taskService.deleteTask(task.getId());

        List<Task> remaining = taskService.getAllUserTasks(1L);
        assertEquals(0, remaining.size());
    }
}
