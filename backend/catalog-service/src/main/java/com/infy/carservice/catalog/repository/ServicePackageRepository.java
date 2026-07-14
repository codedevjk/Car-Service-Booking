package com.infy.carservice.catalog.repository;

import com.infy.carservice.catalog.entity.ServicePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicePackageRepository extends JpaRepository<ServicePackage, Long> {
    Page<ServicePackage> findByCategoryIdOrderByAvailabilityStatusDesc(Long categoryId, Pageable pageable);
    Page<ServicePackage> findAllByOrderByAvailabilityStatusDesc(Pageable pageable);

    Page<ServicePackage> findByAvailabilityStatusAndNameContainingIgnoreCase(com.infy.carservice.catalog.entity.AvailabilityStatus status, String name, Pageable pageable);
    Page<ServicePackage> findByAvailabilityStatusAndCategoryIdAndNameContainingIgnoreCase(com.infy.carservice.catalog.entity.AvailabilityStatus status, Long categoryId, String name, Pageable pageable);
    
    Page<ServicePackage> findByAvailabilityStatus(com.infy.carservice.catalog.entity.AvailabilityStatus status, Pageable pageable);
    Page<ServicePackage> findByAvailabilityStatusAndCategoryId(com.infy.carservice.catalog.entity.AvailabilityStatus status, Long categoryId, Pageable pageable);
}
