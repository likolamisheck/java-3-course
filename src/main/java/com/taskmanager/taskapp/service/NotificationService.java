package com.taskmanager.taskapp.service;

import com.taskmanager.taskapp.model.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getAllUserNotifications(Long userId);
    List<Notification> getUnreadNotifications(Long userId);
    void addNotification(Notification notification);
}
