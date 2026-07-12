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
    @GetMapping("/customer/{customerId}")
    public List<VehicleDTO> getVehicles(@PathVariable Long customerId) {
        return vehicleService.getVehiclesByCustomer(customerId);
    }
    @PostMapping
    public VehicleDTO addVehicle(@Valid @RequestBody VehicleDTO dto) {
        return vehicleService.addVehicle(dto);
    }
    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
    }
}
