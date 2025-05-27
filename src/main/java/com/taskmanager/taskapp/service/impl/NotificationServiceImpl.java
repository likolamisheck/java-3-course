package com.taskmanager.taskapp.service.impl;

import com.taskmanager.taskapp.model.Notification;
import com.taskmanager.taskapp.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final Map<Long, Notification> notifications = new HashMap<>();
    private final AtomicLong notificationIdGenerator = new AtomicLong();

    @Override
    public List<Notification> getAllUserNotifications(Long userId) {
        return notifications.values().stream()
                .filter(n -> n.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> getUnreadNotifications(Long userId) {
        return notifications.values().stream()
                .filter(n -> n.getUserId().equals(userId) && !n.isRead())
                .collect(Collectors.toList());
    }

    @Override
    public void addNotification(Notification notification) {
        notification.setId(notificationIdGenerator.incrementAndGet());
        notification.setCreatedAt(java.time.LocalDateTime.now());
        notifications.put(notification.getId(), notification);
    }
}
