package com.infy.carservice.catalog.controller;
import com.infy.carservice.catalog.dto.CarServiceDTO;
import com.infy.carservice.catalog.service.CarServiceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/services")
public class CarServiceController {
    
    @Autowired
    private CarServiceManagementService serviceManagement;

    // TODO: Trainee to implement US05 (Car Service Management)
    // 1. Map @GetMapping to retrieve all services.
    // 2. Map @PostMapping to add a new service.
    // 3. Map @PutMapping("/{id}") to update a service.
    // 4. Map @DeleteMapping("/{id}") to delete a service.
    
    // TODO: Trainee to implement US06 (Browse & Search Services)
    // 1. Map endpoints for searching services by name.
    // 2. Map endpoints for filtering services by category.
}
