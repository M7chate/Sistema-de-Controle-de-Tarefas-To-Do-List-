// TaskService.java
package com.todoapp.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todoapp.dto.TaskRequest;
import com.todoapp.dto.TaskResponse;
import com.todoapp.entity.Category;
import com.todoapp.entity.Task;
import com.todoapp.entity.Task.TaskPriority;
import com.todoapp.entity.TaskCategory;
import com.todoapp.entity.User;
import com.todoapp.repository.CategoryRepository;
import com.todoapp.repository.TaskRepository;

@Service
@Transactional
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    public List<TaskResponse> getAllTasksByUser(User user) {
        List<Task> tasks = taskRepository.findByUserOrderByCreatedAtDesc(user);
        return tasks.stream().map(this::convertToResponse).collect(Collectors.toList());
    }
    
    public TaskResponse getTaskById(Long id, User user) {
        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return convertToResponse(task);
    }
    
    public TaskResponse createTask(TaskRequest request, User user) {
        Task task = new Task(request.getTitle(), request.getDescription(), user);
        task.setIsCompleted(request.getIsCompleted());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        
        task = taskRepository.save(task);
        
        // Add categories if provided
        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            addCategoriesToTask(task, request.getCategoryIds(), user);
        }
        
        return convertToResponse(task);
    }
    
    public TaskResponse updateTask(Long id, TaskRequest request, User user) {
        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setIsCompleted(request.getIsCompleted());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        
        // Update categories
        if (request.getCategoryIds() != null) {
            task.getTaskCategories().clear();
            addCategoriesToTask(task, request.getCategoryIds(), user);
        }
        
        task = taskRepository.save(task);
        return convertToResponse(task);
    }
    
    public void deleteTask(Long id, User user) {
        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(task);
    }
    
    public List<TaskResponse> getTasksByStatus(User user, Boolean isCompleted) {
        List<Task> tasks = taskRepository.findByUserAndIsCompletedOrderByCreatedAtDesc(user, isCompleted);
        return tasks.stream().map(this::convertToResponse).collect(Collectors.toList());
    }
    
    public List<TaskResponse> getTasksByPriority(User user, TaskPriority priority) {
        List<Task> tasks = taskRepository.findByUserAndPriorityOrderByCreatedAtDesc(user, priority);
        return tasks.stream().map(this::convertToResponse).collect(Collectors.toList());
    }
    
    public List<TaskResponse> getTasksByCategory(User user, Long categoryId) {
        List<Task> tasks = taskRepository.findByUserAndCategoryId(user, categoryId);
        return tasks.stream().map(this::convertToResponse).collect(Collectors.toList());
    }
    
    public List<TaskResponse> searchTasks(User user, String keyword) {
        List<Task> tasks = taskRepository.findByUserAndTitleOrDescriptionContaining(user, keyword);
        return tasks.stream().map(this::convertToResponse).collect(Collectors.toList());
    }
    
    public TaskResponse toggleTaskCompletion(Long id, User user) {
        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setIsCompleted(!task.getIsCompleted());
        task = taskRepository.save(task);
        return convertToResponse(task);
    }
    
    private void addCategoriesToTask(Task task, Set<Long> categoryIds, User user) {
        for (Long categoryId : categoryIds) {
            Category category = categoryRepository.findByIdAndUser(categoryId, user)
                    .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));
            TaskCategory taskCategory = new TaskCategory(task, category);
            task.getTaskCategories().add(taskCategory);
        }
    }
    
    private TaskResponse convertToResponse(Task task) {
        TaskResponse response = new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getIsCompleted(),
                task.getPriority(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
        
        if (task.getTaskCategories() != null) {
            Set<TaskResponse.CategoryResponse> categories = task.getTaskCategories().stream()
                    .map(tc -> new TaskResponse.CategoryResponse(
                            tc.getCategory().getId(),
                            tc.getCategory().getName(),
                            tc.getCategory().getColor()
                    ))
                    .collect(Collectors.toSet());
            response.setCategories(categories);
        }
        
        return response;
    }
}
