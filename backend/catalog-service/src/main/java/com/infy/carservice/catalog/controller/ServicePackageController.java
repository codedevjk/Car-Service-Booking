package com.infy.carservice.catalog.controller;

import com.infy.carservice.catalog.dto.ServicePackageDTO;
import com.infy.carservice.catalog.service.ServicePackageManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/service-packages")
public class ServicePackageController {
    
    @Autowired
    private ServicePackageManagementService serviceManagement;

    @GetMapping
    public Page<ServicePackageDTO> getServicePackages(
            @RequestParam(required = false) Long categoryId,
            @org.springframework.data.web.PageableDefault(size = 20) org.springframework.data.domain.Pageable pageable) {
        return serviceManagement.getServicePackages(categoryId, pageable);
    }

    @GetMapping("/browse")
    public Page<ServicePackageDTO> browseServicePackages(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search,
            @org.springframework.data.web.PageableDefault(size = 20) org.springframework.data.domain.Pageable pageable) {
        return serviceManagement.browseServicePackages(categoryId, search, pageable);
    }

    @PostMapping
    public ServicePackageDTO addServicePackage(@Valid @RequestBody ServicePackageDTO dto, @RequestHeader("X-User-Id") String callerId, @RequestHeader(value = "X-User-Role", defaultValue = "CUSTOMER") String userRole) {
        if (!"ADMIN".equals(userRole)) throw new RuntimeException("Unauthorized");
        return serviceManagement.addServicePackage(dto);
    }
    
    @PutMapping("/{id}")
    public ServicePackageDTO updateServicePackage(@PathVariable Long id, @Valid @RequestBody ServicePackageDTO dto, @RequestHeader("X-User-Id") String callerId, @RequestHeader(value = "X-User-Role", defaultValue = "CUSTOMER") String userRole) {
        if (!"ADMIN".equals(userRole)) throw new RuntimeException("Unauthorized");
        return serviceManagement.updateServicePackage(id, dto);
    }
    
    @DeleteMapping("/{id}")
    public void deleteServicePackage(@PathVariable Long id, @RequestHeader("X-User-Id") String callerId, @RequestHeader(value = "X-User-Role", defaultValue = "CUSTOMER") String userRole) {
        if (!"ADMIN".equals(userRole)) throw new RuntimeException("Unauthorized");
        serviceManagement.deleteServicePackage(id);
    }

    @GetMapping("/count")
    public long getServicePackageCount() {
        return serviceManagement.getServicePackages(null, Pageable.unpaged()).getTotalElements();
    }
}
