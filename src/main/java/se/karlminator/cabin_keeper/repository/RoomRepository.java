package se.karlminator.cabin_keeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.karlminator.cabin_keeper.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("SELECT DISTINCT r FROM room r LEFT JOIN FETCH r.products")
    List<Room> findAllWithProducts();

    @Query("SELECT r FROM Room r LEFT JOIN FETCH r.products WHERE r.id = :id")
    Optional<Room> findByIdWithProducts(@Param("id") Integer id);
}
