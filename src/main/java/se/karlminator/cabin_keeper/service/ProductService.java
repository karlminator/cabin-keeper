package se.karlminator.cabin_keeper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.karlminator.cabin_keeper.model.Product;
import se.karlminator.cabin_keeper.model.Room;
import se.karlminator.cabin_keeper.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer id){
        return productRepository.findById(id);
    }

    public List<Product> getProductsByRoom(Room room){
        return productRepository.findByRoom(room);
    }

    public List<Product> getProductsByRoomId(Integer roomId){
        return productRepository.findByRoomId(roomId);
    }

    public List<Product> getProductsByCategoryId(Integer categoryId){
        return productRepository.findByCategoriesId(categoryId);
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public void deleteProduct(Integer id){
        productRepository.deleteById(id);
    }

}
