package se.karlminator.cabin_keeper.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RoomDTO {
    private Integer id;

    @NotBlank(message = "Room name is required")
    @Size(max = 255, message = "Room name cannot exceed 255 characters")
    private String name;

    public RoomDTO(){}

    public RoomDTO(Integer id, String name){
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
}
