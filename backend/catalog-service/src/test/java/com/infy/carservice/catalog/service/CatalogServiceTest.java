package com.infy.carservice.catalog.service;

import com.infy.carservice.catalog.dto.ServiceCategoryDTO;
import com.infy.carservice.catalog.entity.ServiceCategory;
import com.infy.carservice.catalog.repository.ServiceCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.anyString;

import com.infy.carservice.catalog.service.ServiceCategoryService;

class CatalogServiceTest {

    @Mock
    private ServiceCategoryRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ServiceCategoryService catalogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategories() {
        ServiceCategory cat = new ServiceCategory();
        cat.setId(1L);
        cat.setName("Test");

        ServiceCategoryDTO dto = new ServiceCategoryDTO();
        dto.setId(1L);
        dto.setName("Test");

        when(repository.findAll()).thenReturn(Collections.singletonList(cat));
        when(modelMapper.map(cat, ServiceCategoryDTO.class)).thenReturn(dto);

        List<ServiceCategoryDTO> list = catalogService.getAllCategories();
        assertEquals(1, list.size());
        assertEquals("Test", list.get(0).getName());
    }

    @Test
    void testCreateCategory() {
        ServiceCategoryDTO dto = new ServiceCategoryDTO();
        dto.setName("Test");
        
ServiceCategory cat = new ServiceCategory();
        cat.setName("Test");
        
ServiceCategory saved = new ServiceCategory();
        saved.setId(1L);
        saved.setName("Test");

        when(repository.findByName(anyString())).thenReturn(Optional.empty());
        when(modelMapper.map(dto, ServiceCategory.class)).thenReturn(cat);
        when(repository.save(any(ServiceCategory.class))).thenReturn(saved);
        when(modelMapper.map(saved, ServiceCategoryDTO.class)).thenReturn(dto);

        ServiceCategoryDTO result = catalogService.addCategory(dto);
        assertNotNull(result);
    }

    @Test
    void testDeleteCategory() {
        ServiceCategory cat = new ServiceCategory();
        cat.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(cat));
        
assertDoesNotThrow(() -> catalogService.deleteCategory(1L));
        verify(repository, times(1)).save(cat);
        assertFalse(cat.getActive());
    }
}
