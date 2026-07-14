package com.infy.carservice.catalog.repository;

import com.infy.carservice.catalog.entity.ServicePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.infy.carservice.catalog.entity.AvailabilityStatus;

@Repository
public interface ServicePackageRepository extends JpaRepository<ServicePackage, Long> {
    Page<ServicePackage> findByCategoryIdOrderByAvailabilityStatusDesc(Long categoryId, Pageable pageable);
    Page<ServicePackage> findAllByOrderByAvailabilityStatusDesc(Pageable pageable);

    Page<ServicePackage> findByAvailabilityStatusAndNameContainingIgnoreCase(AvailabilityStatus status, String name, Pageable pageable);
    Page<ServicePackage> findByAvailabilityStatusAndCategoryIdAndNameContainingIgnoreCase(AvailabilityStatus status, Long categoryId, String name, Pageable pageable);
    
    Page<ServicePackage> findByAvailabilityStatus(AvailabilityStatus status, Pageable pageable);
    Page<ServicePackage> findByAvailabilityStatusAndCategoryId(AvailabilityStatus status, Long categoryId, Pageable pageable);
}
