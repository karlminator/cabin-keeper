package se.karlminator.cabin_keeper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.karlminator.cabin_keeper.error.ResourceNotFoundException;
import se.karlminator.cabin_keeper.model.Room;
import se.karlminator.cabin_keeper.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    public Room getRoomById(Integer id){
        return roomRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Room not found with id: "+id));
    }

    public Room createRoom(Room room){
        room.setId(null);
        return roomRepository.save(room);
    }

    public Room updateRoom(Integer id, Room roomDetails){
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Room not found with id: "+id));

        existingRoom.setName(roomDetails.getName());
        return roomRepository.save(existingRoom);
    }

    public void deleteRoom(Integer id){
        roomRepository.deleteById(id);
    }
}
