// CategoryService.java
package com.todoapp.service;

import com.todoapp.entity.Category;
import com.todoapp.entity.User;
import com.todoapp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    public List<Category> getAllCategoriesByUser(User user) {
        return categoryRepository.findByUserOrderByNameAsc(user);
    }
    
    public Category getCategoryById(Long id, User user) {
        return categoryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
    
    public Category createCategory(String name, String description, String color, User user) {
        if (categoryRepository.existsByNameAndUser(name, user)) {
            throw new RuntimeException("Category name already exists");
        }
        
        Category category = new Category(name, description, user);
        category.setColor(color);
        return categoryRepository.save(category);
    }
    
    public Category updateCategory(Long id, String name, String description, String color, User user) {
        Category category = categoryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        // Check if name is being changed and if new name already exists
        if (!category.getName().equals(name) && categoryRepository.existsByNameAndUser(name, user)) {
            throw new RuntimeException("Category name already exists");
        }
        
        category.setName(name);
        category.setDescription(description);
        category.setColor(color);
        
        return categoryRepository.save(category);
    }
    
    public void deleteCategory(Long id, User user) {
        Category category = categoryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }
    
    public Category findByNameAndUser(String name, User user) {
        return categoryRepository.findByNameAndUser(name, user)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
    
