package se.karlminator.cabin_keeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.karlminator.cabin_keeper.model.Product;
import se.karlminator.cabin_keeper.model.Room;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.room LEFT JOIN FETCH p.categories")
    List<Product> findAllWithRoomAndCategories();

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.room LEFT JOIN FETCH p.categories WHERE p.id = :id")
    Optional<Product> findByIdWithRoomAndCategories(@Param("id") Integer id);

    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByName(String name);
    List<Product> findByRoomId(Integer roomId);
    List<Product> findByCategoriesId(Integer categoryId);
}
