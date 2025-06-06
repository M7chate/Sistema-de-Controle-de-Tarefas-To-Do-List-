// TaskController.java
package com.todoapp.controller;

import com.todoapp.dto.TaskRequest;
import com.todoapp.dto.TaskResponse;
import com.todoapp.entity.Task.TaskPriority;
import com.todoapp.entity.User;
import com.todoapp.service.AuthService;
import com.todoapp.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tasks", description = "Endpoints para gerenciamento de tarefas")
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = "Bearer Authentication")
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private AuthService authService;
    
    @Operation(summary = "Listar todas as tarefas do usuário")
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(Authentication authentication) {
        User user = authService.findByUsername(authentication.getName());
        List<TaskResponse> tasks = taskService.getAllTasksByUser(user);
        return ResponseEntity.ok(tasks);
    }
    
    @Operation(summary = "Buscar tarefa por ID")
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id, Authentication authentication) {
        User user = authService.findByUsername(authentication.getName());
        TaskResponse task = taskService.getTaskById(id, user);
        return ResponseEntity.ok(task);
    }
    
    @Operation(summary = "Criar nova tarefa")
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest taskRequest, 
                                                  Authentication authentication) {
        User user = authService.findByUsername(authentication.getName());
        TaskResponse task = taskService.createTask(taskRequest, user);
        return ResponseEntity.ok(task);
    }
    
    @Operation(summary = "Atualizar tarefa")
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, 
                                                  @Valid @RequestBody TaskRequest taskRequest,
                                                  Authentication authentication) {
        User user = authService.findByUsername(authentication.getName());
        TaskResponse task = taskService.updateTask(id, taskRequest, user);
        return ResponseEntity.ok(task);
    }
    
    @Operation(summary = "Deletar tarefa")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, Authentication authentication) {
        User user = authService.findByUsername(authentication.getName());
        taskService.deleteTask(id, user);
        return ResponseEntity.ok().build();
    }
    
    @Operation(summary = "Listar tarefas por status")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(@PathVariable Boolean status, 
                                                              Authentication authentication) {
        User user = authService.findByUsername(authentication.getName());
        List<TaskResponse> tasks = taskService.getTasksByStatus(user, status);
        return ResponseEntity.ok(tasks);
    }
    
    @Operation(summary = "Listar tarefas por prioridade")
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TaskResponse>> getTasksByPriority(@PathVariable TaskPriority priority, 
                                                                Authentication authentication) {
        User user = authService.findByUsername(authentication.getName());
        List<TaskResponse> tasks = taskService.getTasksByPriority(user, priority);
        return ResponseEntity.ok(tasks);
    }
    
    @Operation(summary = "Buscar tarefas por palavra-chave")
    @GetMapping("/search")
    public ResponseEntity<List<TaskResponse>> searchTasks(@RequestParam String keyword, 
                                                         Authentication authentication) {
        User user = authService.findByUsername(authentication.getName());
        List<TaskResponse> tasks = taskService.searchTasks(user, keyword);
        return ResponseEntity.ok(tasks);
    }
    
    @Operation(summary = "Alternar status de conclusão da tarefa")
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<TaskResponse> toggleTaskCompletion(@PathVariable Long id, 
                                                            Authentication authentication) {
        User user = authService.findByUsername(authentication.getName());
        TaskResponse task = taskService.toggleTaskCompletion(id, user);
        return ResponseEntity.ok(task);
    }
    
    @Operation(summary = "Listar tarefas por categoria")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<TaskResponse>> getTasksByCategory(@PathVariable Long categoryId, 
                                                                Authentication authentication) {
        User user = authService.findByUsername(authentication.getName());
        List<TaskResponse> tasks = taskService.getTasksByCategory(user, categoryId);
        return ResponseEntity.ok(tasks);
    }
}
