// CategoryController.java
package com.todoapp.controller;

import com.todoapp.entity.Category;
import com.todoapp.entity.User;
import com.todoapp.service.AuthService;
import com.todoapp.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Categories", description = "Endpoints para gerenciamento de categorias")
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = "Bearer Authentication")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private AuthService authService;
    
    @Operation(summary = "Listar todas as categorias do usuário")
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(Authentication authentication) {
        User user = authService.findByUsername(authentication.getName());
        List<Category> categories = categoryService.getAllCategoriesByUser(user);
        return ResponseEntity.ok(categories);
    }
    
    @Operation(summary = "Buscar categoria por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id, Authentication authentication) {
        User user = authService.findByUsername(authentication.getName());
        Category category = categoryService.getCategoryById(id, user);
        return ResponseEntity.ok(category);
    }
    
    @Operation(summary = "Criar nova categoria")
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryRequest categoryRequest, 
                                                  Authentication authentication) {
        User user = authService.findByUsername(authentication.getName());
        Category category = categoryService.createCategory(
            categoryRequest.getName(),
            categoryRequest.getDescription(),
            categoryRequest.getColor(),
            user
        );
        return ResponseEntity.ok(category);
    }
    
    @Operation(summary = "Atualizar categoria")
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, 
                                                  @Valid @RequestBody CategoryRequest categoryRequest,
                                                  Authentication authentication) {
        User user = authService.findByUsername(authentication.getName());
        Category category = categoryService.updateCategory(
            id,
            categoryRequest.getName(),
            categoryRequest.getDescription(),
            categoryRequest.getColor(),
            user
        );
        return ResponseEntity.ok(category);
    }
    
    @Operation(summary = "Deletar categoria")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id, Authentication authentication) {
        User user = authService.findByUsername(authentication.getName());
        categoryService.deleteCategory(id, user);
        return ResponseEntity.ok().build();
    }
    
    // CategoryRequest inner class
    public static class CategoryRequest {
        @jakarta.validation.constraints.NotBlank
        @jakarta.validation.constraints.Size(max = 50)
        private String name;
        
        @jakarta.validation.constraints.Size(max = 255)
        private String description;
        
        @jakarta.validation.constraints.Size(max = 7)
        private String color;
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
    }
}
