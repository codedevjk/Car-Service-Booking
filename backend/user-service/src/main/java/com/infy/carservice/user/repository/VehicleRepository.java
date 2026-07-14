package com.infy.carservice.user.repository;
import com.infy.carservice.user.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByUserId(String userId);
    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);
    long countByUserId(String userId);
}
