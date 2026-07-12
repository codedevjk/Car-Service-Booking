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
    public List<VehicleDTO> getVehiclesByCustomer(Long customerId) {
        return repository.findByCustomerId(customerId).stream()
            .map(v -> modelMapper.map(v, VehicleDTO.class))
            .collect(Collectors.toList());
    }
    public VehicleDTO addVehicle(VehicleDTO dto) {
        if(repository.findByRegistrationNumber(dto.getRegistrationNumber()).isPresent()) {
            throw new RuntimeException("Vehicle registration number must be unique");
        }
        Vehicle v = modelMapper.map(dto, Vehicle.class);
        return modelMapper.map(repository.save(v), VehicleDTO.class);
    }
    public void deleteVehicle(Long id) {
        repository.deleteById(id);
    }
}
