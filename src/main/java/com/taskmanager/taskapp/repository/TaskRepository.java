package com.taskmanager.taskapp.repository;

import com.taskmanager.taskapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserIdAndDeletedFalse(Long userId);
    List<Task> findByUserIdAndCompletedFalseAndDeletedFalse(Long userId);
}
