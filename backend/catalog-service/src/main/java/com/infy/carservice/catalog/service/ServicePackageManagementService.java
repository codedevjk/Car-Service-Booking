package com.infy.carservice.catalog.service;

import com.infy.carservice.catalog.dto.ServicePackageDTO;
import com.infy.carservice.catalog.entity.ServicePackage;
import com.infy.carservice.catalog.repository.ServicePackageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.infy.carservice.catalog.entity.AvailabilityStatus;

@Service
public class ServicePackageManagementService {
    
    @Autowired
    private ServicePackageRepository repository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    public Page<ServicePackageDTO> getServicePackages(Long categoryId, Pageable pageable) {
        Page<ServicePackage> pageResult;
        if (categoryId != null) {
            pageResult = repository.findByCategoryIdOrderByAvailabilityStatusDesc(categoryId, pageable);
        } else {
            pageResult = repository.findAllByOrderByAvailabilityStatusDesc(pageable);
        }
        return pageResult.map(v -> modelMapper.map(v, ServicePackageDTO.class));
    }

    public Page<ServicePackageDTO> browseServicePackages(Long categoryId, String searchQuery, Pageable pageable) {
        Page<ServicePackage> pageResult;
        if (categoryId != null) {
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                pageResult = repository.findByAvailabilityStatusAndCategoryIdAndNameContainingIgnoreCase(AvailabilityStatus.ACTIVE, categoryId, searchQuery, pageable);
            } else {
                pageResult = repository.findByAvailabilityStatusAndCategoryId(AvailabilityStatus.ACTIVE, categoryId, pageable);
            }
        } else {
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                pageResult = repository.findByAvailabilityStatusAndNameContainingIgnoreCase(AvailabilityStatus.ACTIVE, searchQuery, pageable);
            } else {
                pageResult = repository.findByAvailabilityStatus(AvailabilityStatus.ACTIVE, pageable);
            }
        }
        return pageResult.map(v -> modelMapper.map(v, ServicePackageDTO.class));
    }

    public ServicePackageDTO addServicePackage(ServicePackageDTO dto) {
        ServicePackage sp = modelMapper.map(dto, ServicePackage.class);
        if (sp.getAvailabilityStatus() == null) sp.setAvailabilityStatus(AvailabilityStatus.ACTIVE);
        return modelMapper.map(repository.save(sp), ServicePackageDTO.class);
    }

    public ServicePackageDTO updateServicePackage(Long id, ServicePackageDTO dto) {
        ServicePackage sp = repository.findById(id).orElseThrow(() -> new RuntimeException("Service package not found"));
        sp.setName(dto.getName());
        sp.setDescription(dto.getDescription());
        sp.setPrice(dto.getPrice());
        sp.setCategoryId(dto.getCategoryId());
        if (dto.getAvailabilityStatus() != null) sp.setAvailabilityStatus(dto.getAvailabilityStatus());
        return modelMapper.map(repository.save(sp), ServicePackageDTO.class);
    }

    public void deleteServicePackage(Long id) {
        ServicePackage sp = repository.findById(id).orElseThrow(() -> new RuntimeException("Service package not found"));
        
        try {
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("X-User-Id", "A1");
            org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);
            org.springframework.http.ResponseEntity<Long> res = restTemplate.exchange("http://booking-service/api/bookings/active/count?serviceId=" + id, org.springframework.http.HttpMethod.GET, entity, Long.class);
            Long activeBookings = res.getBody();
            if (activeBookings != null && activeBookings > 0) {
                throw new RuntimeException("Cannot delete service package as it is associated with active bookings");
            }
        } catch (org.springframework.web.client.RestClientException e) {
            throw new RuntimeException("Validation failed: Could not verify active bookings");
        }

        sp.setAvailabilityStatus(AvailabilityStatus.INACTIVE);
        repository.save(sp);
    }
}
