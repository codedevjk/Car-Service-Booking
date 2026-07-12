package com.infy.carservice.catalog.service;
import com.infy.carservice.catalog.dto.ServiceCategoryDTO;
import com.infy.carservice.catalog.entity.ServiceCategory;
import com.infy.carservice.catalog.repository.ServiceCategoryRepository;
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
    private ModelMapper modelMapper;
    public List<ServiceCategoryDTO> getAllCategories() {
        return repository.findAll().stream()
            .map(c -> modelMapper.map(c, ServiceCategoryDTO.class))
            .collect(Collectors.toList());
    }
    public ServiceCategoryDTO addCategory(ServiceCategoryDTO dto) {
        if(repository.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException("Category name must be unique");
        }
        ServiceCategory c = modelMapper.map(dto, ServiceCategory.class);
        return modelMapper.map(repository.save(c), ServiceCategoryDTO.class);
    }
    public void deleteCategory(Long id) {
        // TODO: Validate if active services exist before deleting
        repository.deleteById(id);
    }
}
