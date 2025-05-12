package se.karlminator.cabin_keeper.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.karlminator.cabin_keeper.dto.CategoryDTO;
import se.karlminator.cabin_keeper.model.Category;

@Component
public class CategoryMapper {
    private final ProductMapper productMapper;

    @Autowired
    public CategoryMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

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

        return dto;
    }
}
