package se.karlminator.cabin_keeper.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.karlminator.cabin_keeper.dto.ProductDTO;
import se.karlminator.cabin_keeper.model.Category;
import se.karlminator.cabin_keeper.model.Product;
import se.karlminator.cabin_keeper.service.CategoryService;
import se.karlminator.cabin_keeper.service.RoomService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    private final RoomService roomService;
    private final CategoryService categoryService;

    @Autowired
    public ProductMapper(RoomService roomService, CategoryService categoryService){
        this.roomService = roomService;
        this.categoryService = categoryService;
    }

    public Product toEntity(ProductDTO dto){
        if(dto == null){
            return null;
        }

        Product p = new Product();
        p.setId(dto.getId());
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setComment(dto.getComment());
        p.setStock(dto.getStock() != null ? dto.getStock() : 0);

        if(dto.getRoomId() != null){
            roomService.getRoomById(dto.getRoomId()).ifPresent(p::setRoom);
        }

        if(dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()){
            Set<Category> categories = new HashSet<>();
            for(Integer categoryId : dto.getCategoryIds()){
                categoryService.getCategoryById(categoryId).ifPresent(categories::add);
            }
            p.setCategories(categories);
        }

        return p;
    }

    public ProductDTO toDto(Product e){
        if (e == null){
            return null;
        }

        ProductDTO dto = new ProductDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setDescription(e.getDescription());
        dto.setComment(e.getComment());
        dto.setStock(e.getStock());

        if(e.getRoom() != null){
            dto.setRoomId(e.getRoom().getId());
            dto.setRoomName(e.getRoom().getName());
        }

        if(e.getCategories() != null && !e.getCategories().isEmpty()){
            dto.setCategoryIds(e.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet()));

            dto.setCategoryNames(e.getCategories().stream()
                    .map(Category::getName)
                    .collect(Collectors.toSet()));
        }
        
        return dto;
    }
}
