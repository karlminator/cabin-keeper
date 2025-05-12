package se.karlminator.cabin_keeper.dto;

import java.util.HashSet;
import java.util.Set;

public class CategoryDTO  extends CategorySlimDTO {

    private Set<ProductSlimDTO> products = new HashSet<>();

    public CategoryDTO(){}

    public CategoryDTO(Integer id, String name){
        super(id, name);
    }

    public Set<ProductSlimDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductSlimDTO> products) {
        this.products = products;
    }
}
