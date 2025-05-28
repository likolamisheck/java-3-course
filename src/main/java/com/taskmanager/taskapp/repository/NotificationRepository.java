package com.taskmanager.taskapp.repository;

import com.taskmanager.taskapp.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
    List<Notification> findByUserIdAndReadFalse(Long userId);
}
