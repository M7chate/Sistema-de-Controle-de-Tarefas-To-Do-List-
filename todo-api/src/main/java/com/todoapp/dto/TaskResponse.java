// TaskResponse.java
package com.todoapp.dto;

import com.todoapp.entity.Task.TaskPriority;
import java.time.LocalDateTime;
import java.util.Set;

public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private Boolean isCompleted;
    private TaskPriority priority;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<CategoryResponse> categories;
    
    public TaskResponse() {}
    
    public TaskResponse(Long id, String title, String description, Boolean isCompleted, 
                       TaskPriority priority, LocalDateTime dueDate, LocalDateTime createdAt, 
                       LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Boolean getIsCompleted() { return isCompleted; }
    public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }
    
    public TaskPriority getPriority() { return priority; }
    public void setPriority(TaskPriority priority) { this.priority = priority; }
    
    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public Set<CategoryResponse> getCategories() { return categories; }
    public void setCategories(Set<CategoryResponse> categories) { this.categories = categories; }
    
    // CategoryResponse inner class
    public static class CategoryResponse {
        private Long id;
        private String name;
        private String color;
        
        public CategoryResponse() {}
        
        public CategoryResponse(Long id, String name, String color) {
            this.id = id;
            this.name = name;
            this.color = color;
        }
        
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
    }
}
