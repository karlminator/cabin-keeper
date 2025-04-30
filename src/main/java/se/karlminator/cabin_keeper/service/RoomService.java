package se.karlminator.cabin_keeper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Optional<Room> getRoomById(Integer id){
        return roomRepository.findById(id);
    }

    public Optional<Room> getRoomByName(String name){
        return roomRepository.findByName(name);
    }

    public Room saveRoom(Room room){
        return roomRepository.save(room);
    }

    public void deleteRoom(Integer id){
        roomRepository.deleteById(id);
    }
}
