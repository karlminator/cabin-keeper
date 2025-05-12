package se.karlminator.cabin_keeper.mapper;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import se.karlminator.cabin_keeper.dto.ProductSlimDTO;
import se.karlminator.cabin_keeper.dto.RoomDTO;
import se.karlminator.cabin_keeper.model.Room;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoomMapper {

    public Room toEntity(RoomDTO dto){
        if (dto == null){
            return null;
        }

        Room room = new Room();
        room.setId(dto.getId());
        room.setName(dto.getName());

        return room;
    }

    public RoomDTO toDto(Room room){
        if (room == null){
            return null;
        }

        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());

        if(room.getProducts() != null
                && Hibernate.isInitialized(room.getProducts())
                && !room.getProducts().isEmpty()){

            Set<ProductSlimDTO> productDTOs = room.getProducts().stream()
                    .map(product -> new ProductSlimDTO(product.getId(), product.getName()))
                    .sorted(Comparator.comparing(ProductSlimDTO::getId))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            dto.setProducts(productDTOs);
        }

        return dto;
    }
}
