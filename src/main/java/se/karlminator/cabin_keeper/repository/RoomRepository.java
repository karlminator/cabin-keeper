package se.karlminator.cabin_keeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.karlminator.cabin_keeper.model.Room;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
