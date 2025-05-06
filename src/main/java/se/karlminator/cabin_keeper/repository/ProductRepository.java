package se.karlminator.cabin_keeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.karlminator.cabin_keeper.model.Product;
import se.karlminator.cabin_keeper.model.Room;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByName(String name);
    List<Product> findByRoom(Room room);
    List<Product> findByRoomId(Integer roomId);
    List<Product> findByCategoriesId(Integer categoryId);
}
