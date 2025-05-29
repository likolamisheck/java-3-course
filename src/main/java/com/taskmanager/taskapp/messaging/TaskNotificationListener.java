package com.taskmanager.taskapp.messaging;

import com.taskmanager.taskapp.model.Task;
import com.taskmanager.taskapp.model.Notification;
import com.taskmanager.taskapp.repository.NotificationRepository;
import com.taskmanager.taskapp.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskNotificationListener {

    private final NotificationRepository notificationRepository;

    public TaskNotificationListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.TASK_QUEUE)
    public void handleTaskCreated(Task task) {
        Notification notification = new Notification(
                task.getUserId(),
                "New Task Created: " + task.getDescription(),
                LocalDateTime.now()
        );
        notificationRepository.save(notification);
    }
}
