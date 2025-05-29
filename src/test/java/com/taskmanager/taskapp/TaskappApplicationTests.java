package com.taskmanager.taskapp;

import com.taskmanager.taskapp.service.TaskService;
import com.taskmanager.taskapp.model.Task;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class TaskappApplicationTests {

    @Test
    void testGetPendingTasks() {
        // Arrange
        TaskService taskService = Mockito.mock(TaskService.class);
        Task sampleTask = new Task();
        sampleTask.setTitle("Mock Task"); // works only if Lombok or setter exists
        sampleTask.setCompleted(false);
        sampleTask.setUserId(1L); // use Long, not String

        Mockito.when(taskService.getPendingTasks(1L)) // must match the method's expected type
               .thenReturn(List.of(sampleTask));

        // Act
        List<Task> result = taskService.getPendingTasks(1L);

        // Assert
        assert result.size() == 1;
        assert !result.get(0).isCompleted();
    }
}
