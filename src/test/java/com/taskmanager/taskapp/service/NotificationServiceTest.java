package com.taskmanager.taskapp.service;

import com.taskmanager.taskapp.model.Notification;
import com.taskmanager.taskapp.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationServiceTest {

    private NotificationService notificationService;

    @BeforeEach
    public void setup() {
        notificationService = new NotificationServiceImpl();
    }

    @Test
    public void testAddNotification() {
        Notification n = new Notification(null, 1L, "Test message", LocalDateTime.now());
        notificationService.addNotification(n);

        List<Notification> all = notificationService.getAllUserNotifications(1L);
        assertEquals(1, all.size());
        assertEquals("Test message", all.get(0).getMessage());
    }

    @Test
    public void testGetUnreadNotifications() {
        Notification read = new Notification(null, 1L, "Read one", LocalDateTime.now());
        read.setRead(true);

        Notification unread = new Notification(null, 1L, "Unread one", LocalDateTime.now());

        notificationService.addNotification(read);
        notificationService.addNotification(unread);

        List<Notification> unreadList = notificationService.getUnreadNotifications(1L);
        assertEquals(1, unreadList.size());
        assertEquals("Unread one", unreadList.get(0).getMessage());
    }

    @Test
    public void testNoNotifications() {
        List<Notification> all = notificationService.getAllUserNotifications(99L);
        assertTrue(all.isEmpty());
    }
}
