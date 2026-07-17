package com.infy.carservice.catalog.controller;

import com.infy.carservice.catalog.dto.ServicePackageDTO;
import com.infy.carservice.catalog.service.ServicePackageManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/service-packages")
public class ServicePackageController {
    
    @Autowired
    private ServicePackageManagementService service;

    @GetMapping
    public Page<ServicePackageDTO> getAllServicePackages(
            @RequestParam(required = false) Long categoryId,
            @PageableDefault(size = 20) Pageable pageable) {
        return service.getServicePackages(categoryId, pageable);
    }
    
    @GetMapping("/browse")
    public Page<ServicePackageDTO> browseServicePackages(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 20) Pageable pageable) {
        return service.browseServicePackages(categoryId, search, pageable);
    }

    @PostMapping
    public ServicePackageDTO addServicePackage(@Valid @RequestBody ServicePackageDTO dto, @RequestHeader("X-User-Id") String callerId, @RequestHeader(value = "X-User-Role", defaultValue = "CUSTOMER") String userRole) {
        if (!"ADMIN".equals(userRole)) throw new RuntimeException("Unauthorized");
        return service.addServicePackage(dto);
    }
    
    @PutMapping("/{id}")
    public ServicePackageDTO updateServicePackage(@PathVariable Long id, @Valid @RequestBody ServicePackageDTO dto, @RequestHeader("X-User-Id") String callerId, @RequestHeader(value = "X-User-Role", defaultValue = "CUSTOMER") String userRole) {
        if (!"ADMIN".equals(userRole)) throw new RuntimeException("Unauthorized");
        return service.updateServicePackage(id, dto);
    }
    
    @DeleteMapping("/{id}")
    public void deleteServicePackage(@PathVariable Long id, @RequestHeader("X-User-Id") String callerId, @RequestHeader(value = "X-User-Role", defaultValue = "CUSTOMER") String userRole) {
        if (!"ADMIN".equals(userRole)) throw new RuntimeException("Unauthorized");
        service.deleteServicePackage(id);
    }

    @GetMapping("/count")
    public long getServicePackageCount() {
        return service.getServicePackages(null, Pageable.unpaged()).getTotalElements();
    }

    @GetMapping("/{id}")
    public ServicePackageDTO getServicePackageById(@PathVariable Long id) {
        return service.getServicePackageById(id);
    }
}
