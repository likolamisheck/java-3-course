package com.taskmanager.taskapp.service;

import com.taskmanager.taskapp.model.Notification;
import com.taskmanager.taskapp.repository.NotificationRepository;
import com.taskmanager.taskapp.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {

    private NotificationRepository notificationRepository;
    private NotificationService notificationService;

    @BeforeEach
    public void setup() {
        notificationRepository = mock(NotificationRepository.class);
        notificationService = new NotificationServiceImpl(notificationRepository);
    }

    @Test
    public void testAddNotification() {
        Notification n = new Notification();
        n.setUserId(1L);
        n.setMessage("Test message");
        n.setCreatedAt(LocalDateTime.now());

        when(notificationRepository.save(n)).thenReturn(n);
        notificationService.addNotification(n);

        verify(notificationRepository, times(1)).save(n);
    }

    @Test
    public void testGetUnreadNotifications() {
        Notification unread = new Notification();
        unread.setUserId(1L);
        unread.setMessage("Unread one");
        unread.setCreatedAt(LocalDateTime.now());
        unread.setRead(false);

        when(notificationRepository.findByUserIdAndReadFalse(1L))
                .thenReturn(Collections.singletonList(unread));

        List<Notification> result = notificationService.getUnreadNotifications(1L);

        assertEquals(1, result.size());
        assertEquals("Unread one", result.get(0).getMessage());
    }

    @Test
    public void testGetAllUserNotifications() {
        Notification note1 = new Notification();
        note1.setUserId(1L);
        note1.setMessage("Message 1");

        Notification note2 = new Notification();
        note2.setUserId(1L);
        note2.setMessage("Message 2");

        when(notificationRepository.findByUserId(1L)).thenReturn(Arrays.asList(note1, note2));

        List<Notification> result = notificationService.getAllUserNotifications(1L);
        assertEquals(2, result.size());
    }

    @Test
    public void testNoNotifications() {
        when(notificationRepository.findByUserId(99L)).thenReturn(Collections.emptyList());

        List<Notification> all = notificationService.getAllUserNotifications(99L);
        assertTrue(all.isEmpty());
    }
}
