package se.karlminator.cabin_keeper.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public class ProductDTO extends ProductSlimDTO {

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String comment;

    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    private RoomSlimDTO room;

    private Set<CategorySlimDTO> categories = new HashSet<>();

    // Constructors
    public ProductDTO(){}

    public ProductDTO(Integer id, String name, String description, String comment,
                      Integer stock){
        super(id, name);
        this.description = description;
        this.comment = comment;
        this.stock = stock;
    }

    // Getters and Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public RoomSlimDTO getRoom() {
        return room;
    }

    public void setRoom(RoomSlimDTO room) {
        this.room = room;
    }

    public Set<CategorySlimDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategorySlimDTO> categories) {
        this.categories = categories;
    }
}
