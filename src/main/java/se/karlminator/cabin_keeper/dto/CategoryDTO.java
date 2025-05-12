package se.karlminator.cabin_keeper.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public class CategoryDTO {
    private Integer id;

    @NotBlank(message = "Category name is required")
    @Size(max = 255, message = "Category name cannot exceed 255 characters")
    private String name;

    private Set<ProductSummaryDTO> products = new HashSet<>();

    public CategoryDTO(){}

    public CategoryDTO(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProductSummaryDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductSummaryDTO> products) {
        this.products = products;
    }
}
