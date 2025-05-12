package se.karlminator.cabin_keeper.mapper;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import se.karlminator.cabin_keeper.dto.CategoryDTO;
import se.karlminator.cabin_keeper.dto.ProductBriefDTO;
import se.karlminator.cabin_keeper.model.Category;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryDTO dto){
        if (dto == null){
            return null;
        }

        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());

        return category;
    }

    public CategoryDTO toDto(Category category){
        if (category == null){
            return null;
        }

        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());

        if(category.getProducts() != null
                && Hibernate.isInitialized(category.getProducts())
                && !category.getProducts().isEmpty()){

            Set<ProductBriefDTO> productDTOs = category.getProducts().stream()
                    .map(product -> new ProductBriefDTO(product.getId(), product.getName()))
                    .collect(Collectors.toSet());
            dto.setProducts(productDTOs);
        }

        return dto;
    }
}
