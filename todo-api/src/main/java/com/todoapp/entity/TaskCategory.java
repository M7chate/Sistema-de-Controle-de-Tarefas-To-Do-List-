// TaskCategory.java
package com.todoapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "task_categories")
public class TaskCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    public TaskCategory() {}
    
    public TaskCategory(Task task, Category category) {
        this.task = task;
        this.category = category;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }
    
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
