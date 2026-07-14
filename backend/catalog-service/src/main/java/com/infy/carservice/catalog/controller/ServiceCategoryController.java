package com.infy.carservice.catalog.controller;
import com.infy.carservice.catalog.dto.ServiceCategoryDTO;
import com.infy.carservice.catalog.service.ServiceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/api/categories")
public class ServiceCategoryController {
    @Autowired
    private ServiceCategoryService categoryService;
    @GetMapping
    public List<ServiceCategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }
    @PostMapping
    public ServiceCategoryDTO addCategory(@Valid @RequestBody ServiceCategoryDTO dto, @RequestHeader("X-User-Id") String callerId, @RequestHeader(value = "X-User-Role", defaultValue = "CUSTOMER") String userRole) {
        if (!"ADMIN".equals(userRole)) throw new RuntimeException("Unauthorized");
        return categoryService.addCategory(dto);
    }
    @PutMapping("/{id}")
    public ServiceCategoryDTO updateCategory(@PathVariable Long id, @Valid @RequestBody ServiceCategoryDTO dto, @RequestHeader("X-User-Id") String callerId, @RequestHeader(value = "X-User-Role", defaultValue = "CUSTOMER") String userRole) {
        if (!"ADMIN".equals(userRole)) throw new RuntimeException("Unauthorized");
        return categoryService.updateCategory(id, dto);
    }
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id, @RequestHeader("X-User-Id") String callerId, @RequestHeader(value = "X-User-Role", defaultValue = "CUSTOMER") String userRole) {
        if (!"ADMIN".equals(userRole)) throw new RuntimeException("Unauthorized");
        categoryService.deleteCategory(id);
    }
    
    @GetMapping("/count")
    public long getCategoryCount() {
        return categoryService.getAllCategories().size();
    }
}
