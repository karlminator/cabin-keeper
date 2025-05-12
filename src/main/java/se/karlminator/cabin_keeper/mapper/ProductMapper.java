package se.karlminator.cabin_keeper.mapper;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.karlminator.cabin_keeper.dto.*;
import se.karlminator.cabin_keeper.error.ResourceNotFoundException;
import se.karlminator.cabin_keeper.model.Category;
import se.karlminator.cabin_keeper.model.Product;
import se.karlminator.cabin_keeper.model.Room;
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

        if(dto.getRoom() != null && dto.getRoom().getId() != null){
            try{
                Room room = roomService.getRoomById(dto.getRoom().getId());
                p.setRoom(room);
            } catch (ResourceNotFoundException e){
                //TODO: add logging instead of console msg
                System.out.println(e.getMessage());
            }
        }

        if(dto.getCategories() != null && !dto.getCategories().isEmpty()){
            Set<Category> categories = new HashSet<>();
            for(CategorySlimDTO categoryDTO : dto.getCategories()){
                if (categoryDTO.getId() != null){
                    try{
                        Category category = categoryService.getCategoryById(categoryDTO.getId());
                        categories.add(category);

                    }catch (ResourceNotFoundException e){
                        //TODO: add logging instead of console msg
                        System.out.println(e.getMessage());
                    }
                }
            }
            p.setCategories(categories);
        }

        return p;
    }

    public ProductDTO toDto(Product product){
        if (product == null){
            return null;
        }

        ProductDTO dto = new ProductDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setComment(product.getComment());
        dto.setStock(product.getStock());

        if(product.getRoom() != null){
            RoomSlimDTO roomDTO = new RoomSlimDTO();

            roomDTO.setId(product.getRoom().getId());
            roomDTO.setName(product.getRoom().getName());

            dto.setRoom(roomDTO);
        }

        if(product.getCategories() != null
                && Hibernate.isInitialized(product.getCategories())
                && !product.getCategories().isEmpty()){
          Set<CategorySlimDTO> categoryDTOs = product.getCategories().stream()
                  .map(category -> new CategorySlimDTO(category.getId(), category.getName()))
                  .collect(Collectors.toSet());
          dto.setCategories(categoryDTOs);
        }
        
        return dto;
    }
}
