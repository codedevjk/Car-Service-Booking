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
    public ServiceCategoryDTO addCategory(@Valid @RequestBody ServiceCategoryDTO dto) {
        return categoryService.addCategory(dto);
    }
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
