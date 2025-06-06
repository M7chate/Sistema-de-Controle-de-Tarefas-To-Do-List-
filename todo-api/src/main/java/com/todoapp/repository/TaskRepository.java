// TaskRepository.java
package com.todoapp.repository;

import com.todoapp.entity.Task;
import com.todoapp.entity.User;
import com.todoapp.entity.Task.TaskPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserOrderByCreatedAtDesc(User user);
    
    List<Task> findByUserAndIsCompletedOrderByCreatedAtDesc(User user, Boolean isCompleted);
    
    List<Task> findByUserAndPriorityOrderByCreatedAtDesc(User user, TaskPriority priority);
    
    List<Task> findByUserAndDueDateBetweenOrderByDueDateAsc(User user, LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.title LIKE %:keyword% OR t.description LIKE %:keyword%")
    List<Task> findByUserAndTitleOrDescriptionContaining(@Param("user") User user, @Param("keyword") String keyword);
    
    Optional<Task> findByIdAndUser(Long id, User user);
    
    long countByUserAndIsCompleted(User user, Boolean isCompleted);
    
    @Query("SELECT t FROM Task t JOIN t.taskCategories tc WHERE t.user = :user AND tc.category.id = :categoryId")
    List<Task> findByUserAndCategoryId(@Param("user") User user, @Param("categoryId") Long categoryId);
}
