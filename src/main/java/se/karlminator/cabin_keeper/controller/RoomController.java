package se.karlminator.cabin_keeper.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.karlminator.cabin_keeper.dto.RoomDTO;
import se.karlminator.cabin_keeper.mapper.RoomMapper;
import se.karlminator.cabin_keeper.model.Room;
import se.karlminator.cabin_keeper.service.RoomService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*") // TODO: setup config
public class RoomController {

    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @Autowired
    public RoomController(RoomService roomService, RoomMapper roomMapper) {
        this.roomService = roomService;
        this.roomMapper = roomMapper;
    }

    // GET
    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        List<Room> rooms = roomService.getAllRoomsWithProducts();
        List<RoomDTO> roomDTOs = rooms.stream()
                .map(roomMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roomDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Integer id) {
        Room room = roomService.getRoomByIdWithProducts(id);
        return ResponseEntity.ok(roomMapper.toDto(room));
    }

    // POST
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room){
        Room newRoom = roomService.createRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRoom);
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Integer id, @Valid @RequestBody RoomDTO roomDTO){
        Room room = roomMapper.toEntity(roomDTO);
        Room updatedRoom = roomService.updateRoom(id, room);
        return ResponseEntity.ok(roomMapper.toDto(updatedRoom));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Integer id){
        boolean success = roomService.deleteRoom(id);
        if (success){
            return ResponseEntity.noContent().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }

}
