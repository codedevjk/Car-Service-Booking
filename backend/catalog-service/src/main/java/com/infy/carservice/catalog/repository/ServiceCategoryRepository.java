package com.infy.carservice.catalog.repository;
import com.infy.carservice.catalog.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {
    Optional<ServiceCategory> findByName(String name);
}
