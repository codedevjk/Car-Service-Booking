package com.infy.carservice.catalog.service;

import com.infy.carservice.catalog.dto.ServiceCategoryDTO;
import com.infy.carservice.catalog.entity.ServiceCategory;
import com.infy.carservice.catalog.repository.ServiceCategoryRepository;
import com.infy.carservice.catalog.repository.ServicePackageRepository;
import com.infy.carservice.catalog.entity.AvailabilityStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceCategoryService {

    @Autowired
    private ServiceCategoryRepository repository;

    @Autowired
    private ServicePackageRepository servicePackageRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ServiceCategoryDTO> getAllCategories() {
        return repository.findAll().stream()
                .sorted((c1, c2) -> Boolean.compare(c2.getActive() != null && c2.getActive(), c1.getActive() != null && c1.getActive()))
                .map(c -> modelMapper.map(c, ServiceCategoryDTO.class))
                .collect(Collectors.toList());
    }

    public ServiceCategoryDTO addCategory(ServiceCategoryDTO dto) {
        if (repository.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException("Category name must be unique");
        }
        ServiceCategory c = modelMapper.map(dto, ServiceCategory.class);
        if (c.getActive() == null) {
            c.setActive(true);
        }
        return modelMapper.map(repository.save(c), ServiceCategoryDTO.class);
    }

    public ServiceCategoryDTO updateCategory(Long id, ServiceCategoryDTO dto) {
        ServiceCategory category = repository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        if (dto.getActive() != null) {
            category.setActive(dto.getActive());
        }
        return modelMapper.map(repository.save(category), ServiceCategoryDTO.class);
    }

    public void deleteCategory(Long id) {
        ServiceCategory category = repository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        // Ensure no active services exist under this category
        long activeCount = servicePackageRepository.findByAvailabilityStatusAndCategoryId(AvailabilityStatus.ACTIVE, id, org.springframework.data.domain.Pageable.unpaged()).getTotalElements();
        if (activeCount > 0) {
            throw new RuntimeException("Cannot delete category containing active services");
        }

        category.setActive(false);
        repository.save(category);
    }
}
