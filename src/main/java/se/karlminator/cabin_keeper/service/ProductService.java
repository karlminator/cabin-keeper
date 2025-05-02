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
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByRoom(Room room) {
        return productRepository.findByRoom(room);
    }

    public List<Product> getProductsByRoomId(Integer roomId) {
        return productRepository.findByRoomId(roomId);
    }

    public List<Product> getProductsByCategoryId(Integer categoryId) {
        return productRepository.findByCategoriesId(categoryId);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Creates a new product if it doesn't exist yet
     * @param product the product to create
     * @return the saved product
     */
    public Product createProduct(Product product) {
        product.setId(null); // Set ID to null to ensure a new entity is created
        return productRepository.save(product);
    }

    /**
     * Updates an existing product if it exists
     * @param id the ID of the product to update
     * @param productDetails the updated product details
     * @return Optional containing the updated product, or empty if product doesn't exist
     */
    public Optional<Product> updateProduct(Integer id, Product productDetails) {
        return productRepository.findById(id)
                .map(existingProduct -> {
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
                });
    }

    /**
     * Deletes a product if it exists
     * @param id the ID of the product to delete
     * @return true if product was deleted, false if it didn't exist
     */
    public boolean deleteProduct(Integer id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Updates the stock of a product
     * @param id the product ID
     * @param quantity the quantity to add (positive) or remove (negative)
     * @return Optional containing the updated product, or empty if product doesn't exist
     */
    public Optional<Product> updateStock(Integer id, Integer quantity) {
        return productRepository.findById(id)
                .map(product -> {
                    int newStock = product.getStock() + quantity;
                    // Ensure stock doesn't go below 0
                    product.setStock(Math.max(0, newStock));
                    return productRepository.save(product);
                });
    }
}