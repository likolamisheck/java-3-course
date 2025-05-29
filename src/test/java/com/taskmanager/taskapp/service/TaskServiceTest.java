package com.taskmanager.taskapp.service;

import com.taskmanager.taskapp.model.Task;
import com.taskmanager.taskapp.repository.TaskRepository;
import com.taskmanager.taskapp.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    private TaskRepository taskRepository;
    private RabbitTemplate rabbitTemplate;
    private TaskService taskService;

    @BeforeEach
    public void setup() {
        taskRepository = mock(TaskRepository.class);
        rabbitTemplate = mock(RabbitTemplate.class);
        taskService = new TaskServiceImpl(taskRepository, rabbitTemplate);
    }

    @Test
    public void testAddTask() {
        Task task = new Task();
        task.setUserId(1L);
        task.setDescription("Test task");
        task.setTargetDate(LocalDateTime.now().plusDays(1));

        when(taskRepository.save(task)).thenReturn(task);

        Task created = taskService.addTask(task);

        verify(taskRepository).save(task);
        verify(rabbitTemplate).convertAndSend(
            any(String.class),
            any(String.class),
            any(Object.class)
        );
        assertEquals("Test task", created.getDescription());
        assertEquals(1L, created.getUserId());
    }

    @Test
    public void testGetAllUserTasks() {
        Task t1 = new Task();
        t1.setUserId(1L);
        t1.setDescription("T1");

        Task t2 = new Task();
        t2.setUserId(1L);
        t2.setDescription("T2");

        when(taskRepository.findByUserIdAndDeletedFalse(1L)).thenReturn(Arrays.asList(t1, t2));

        List<Task> tasks = taskService.getAllUserTasks(1L);
        assertEquals(2, tasks.size());
    }

    @Test
    public void testGetPendingTasks() {
        Task pending = new Task();
        pending.setUserId(1L);
        pending.setCompleted(false);
        pending.setDescription("Active");

        when(taskRepository.findByUserIdAndCompletedFalseAndDeletedFalse(1L))
                .thenReturn(Collections.singletonList(pending));

        List<Task> result = taskService.getPendingTasks(1L);
        assertEquals(1, result.size());
        assertEquals("Active", result.get(0).getDescription());
    }

    @Test
    public void testDeleteTask() {
        Task task = new Task();
        task.setId(100L);
        task.setUserId(1L);
        task.setDeleted(false);

        when(taskRepository.findById(100L)).thenReturn(Optional.of(task));

        taskService.deleteTask(100L);

        assertTrue(task.isDeleted());
        verify(taskRepository).save(task);
    }
}
