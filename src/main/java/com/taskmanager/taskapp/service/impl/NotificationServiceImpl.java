package com.taskmanager.taskapp.service.impl;

import com.taskmanager.taskapp.model.Notification;
import com.taskmanager.taskapp.repository.NotificationRepository;
import com.taskmanager.taskapp.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> getAllUserNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndReadFalse(userId);
    }

    @Override
    public void addNotification(Notification notification) {
        notificationRepository.save(notification);
    }
}
