package com.infy.carservice.user.repository;
import com.infy.carservice.user.entity.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {
    Optional<CustomerProfile> findByEmail(String email);
    Optional<CustomerProfile> findByUserId(String userId);
    List<CustomerProfile> findByFullNameContainingIgnoreCase(String name);
}
