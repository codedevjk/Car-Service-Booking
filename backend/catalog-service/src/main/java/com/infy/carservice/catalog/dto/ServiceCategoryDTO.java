package com.infy.carservice.catalog.dto;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
@Data
public class ServiceCategoryDTO {
    private Long id;
    @NotBlank
    private String name;
    private String description;
    private Boolean active;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
