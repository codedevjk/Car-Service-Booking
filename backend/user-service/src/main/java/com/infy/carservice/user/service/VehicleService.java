package com.infy.carservice.user.service;
import com.infy.carservice.user.dto.VehicleDTO;
import com.infy.carservice.user.entity.Vehicle;
import com.infy.carservice.user.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class VehicleService {
    @Autowired
    private VehicleRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    public List<VehicleDTO> getVehiclesByUserId(String userId, String callerId, String userRole) {
        if (!"ADMIN".equals(userRole) && !userId.equals(callerId)) {
            throw new RuntimeException("Unauthorized");
        }
        return repository.findByUserId(userId).stream()
            .map(v -> modelMapper.map(v, VehicleDTO.class))
            .collect(Collectors.toList());
    }
    public VehicleDTO addVehicle(VehicleDTO dto, String callerId) {
        if (!dto.getUserId().equals(callerId)) {
            throw new RuntimeException("Unauthorized to add vehicle for this user");
        }
        if(repository.findByRegistrationNumber(dto.getRegistrationNumber()).isPresent()) {
            throw new RuntimeException("Vehicle registration number must be unique");
        }
        Vehicle v = modelMapper.map(dto, Vehicle.class);
        return modelMapper.map(repository.save(v), VehicleDTO.class);
    }
    public void deleteVehicle(Long id, String callerId) {
        Vehicle vehicle = repository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));
        if (!vehicle.getUserId().equals(callerId)) {
            throw new RuntimeException("Unauthorized to delete this vehicle");
        }
        repository.deleteById(id);
    }

    public VehicleDTO getVehicleById(Long id) {
        Vehicle vehicle = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        return modelMapper.map(vehicle, VehicleDTO.class);
    }

    public VehicleDTO updateVehicle(Long id, VehicleDTO dto, String callerId) {
        Vehicle vehicle = repository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));
        if (!vehicle.getUserId().equals(callerId)) {
            throw new RuntimeException("Unauthorized to update this vehicle");
        }
        
        // Ensure no other vehicle has this new registration number
        repository.findByRegistrationNumber(dto.getRegistrationNumber()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new RuntimeException("Vehicle registration number must be unique");
            }
        });
        
        vehicle.setRegistrationNumber(dto.getRegistrationNumber());
        vehicle.setManufacturer(dto.getManufacturer());
        vehicle.setModel(dto.getModel());
        vehicle.setFuelType(dto.getFuelType());
        vehicle.setManufacturingYear(dto.getManufacturingYear());
        
        return modelMapper.map(repository.save(vehicle), VehicleDTO.class);
    }
}
