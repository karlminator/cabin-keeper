package se.karlminator.cabin_keeper.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.karlminator.cabin_keeper.error.ResourceNotFoundException;
import se.karlminator.cabin_keeper.model.Product;
import se.karlminator.cabin_keeper.model.Room;
import se.karlminator.cabin_keeper.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProductsWithDetails(){
        return productRepository.findAllWithRoomAndCategories();
    }

    public Product getProductByIdWithDetails(Integer id){
        return productRepository.findByIdWithRoomAndCategories(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found with id: "+id));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: "+id));
    }

    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> getProductsByRoomId(Integer roomId) {
        return productRepository.findByRoomId(roomId);
    }

    public List<Product> getProductsByCategoryId(Integer categoryId) {
        return productRepository.findByCategoriesId(categoryId);
    }

    public Product createProduct(Product product) {
        product.setId(null); // Set ID to null to ensure a new entity is created
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Integer id, Product productDetails) {
        Product existingProduct = productRepository.findByIdWithRoomAndCategories(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found with id: "+id));

        // Update the properties we want to allow updating
        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setComment(productDetails.getComment());
        existingProduct.setStock(productDetails.getStock());

        // Only update room if one is provided
        if (productDetails.getRoom() != null) {
            existingProduct.setRoom(productDetails.getRoom());
        }

        // Only update categories if provided
        if (productDetails.getCategories() != null && !productDetails.getCategories().isEmpty()) {
            existingProduct.setCategories(productDetails.getCategories());
        }

        return productRepository.save(existingProduct);
    }

    public boolean deleteProduct(Integer id) {
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Product updateStock(Integer id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        int newStock = product.getStock() + quantity;
        product.setStock(Math.max(0, newStock));
        return productRepository.save(product);
    }
}