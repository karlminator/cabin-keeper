package se.karlminator.cabin_keeper.mapper;

import org.springframework.stereotype.Component;
import se.karlminator.cabin_keeper.dto.RoomDTO;
import se.karlminator.cabin_keeper.model.Room;

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

        return dto;
    }
}
