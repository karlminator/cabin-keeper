package se.karlminator.cabin_keeper.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public class RoomDTO extends RoomBriefDTO {

    private Set<ProductBriefDTO> products = new HashSet<>();

    public RoomDTO(){}

    public RoomDTO(Integer id, String name){
        super(id, name);
    }

    public Set<ProductBriefDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductBriefDTO> products) {
        this.products = products;
    }
}
