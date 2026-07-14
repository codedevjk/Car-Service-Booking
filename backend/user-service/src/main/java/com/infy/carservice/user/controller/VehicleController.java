package com.infy.carservice.user.controller;
import com.infy.carservice.user.dto.VehicleDTO;
import com.infy.carservice.user.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;
    @GetMapping("/user/{userId}")
    public List<VehicleDTO> getVehicles(@PathVariable String userId, @RequestHeader("X-User-Id") String callerId, @RequestHeader(value = "X-User-Role", defaultValue = "CUSTOMER") String userRole) {
        return vehicleService.getVehiclesByUserId(userId, callerId, userRole);
    }
    @PostMapping
    public VehicleDTO addVehicle(@Valid @RequestBody VehicleDTO dto, @RequestHeader("X-User-Id") String callerId) {
        return vehicleService.addVehicle(dto, callerId);
    }
    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable Long id, @RequestHeader("X-User-Id") String callerId) {
        vehicleService.deleteVehicle(id, callerId);
    }
    
    @GetMapping("/user/{userId}/count")
    public long getVehicleCount(@PathVariable String userId, @RequestHeader("X-User-Id") String callerId, @RequestHeader(value = "X-User-Role", defaultValue = "CUSTOMER") String userRole) {
        return vehicleService.getVehiclesByUserId(userId, callerId, userRole).size();
    }
}
