package se.karlminator.cabin_keeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.karlminator.cabin_keeper.model.Room;
import se.karlminator.cabin_keeper.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*") // TODO: setup config
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService){
        this.roomService = roomService;
    }

    // GET
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms(){
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Integer id){
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    // POST
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room){
        Room newRoom = roomService.createRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRoom);
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Integer id, @RequestBody Room room){
        Room updatedRoom = roomService.updateRoom(id, room);
        return ResponseEntity.ok(updatedRoom);
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
