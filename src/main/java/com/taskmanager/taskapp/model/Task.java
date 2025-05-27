package com.taskmanager.taskapp.model;

import java.time.LocalDateTime;

public class Task {
    private Long id;
    private Long userId;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime targetDate;
    private boolean completed;
    private boolean deleted;

    public Task() {}

    public Task(Long id, Long userId, String description, LocalDateTime createdAt, LocalDateTime targetDate) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.createdAt = createdAt;
        this.targetDate = targetDate;
        this.completed = false;
        this.deleted = false;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDateTime targetDate) { this.targetDate = targetDate; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}
