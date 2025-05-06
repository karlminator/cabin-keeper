package se.karlminator.cabin_keeper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.karlminator.cabin_keeper.error.ResourceNotFoundException;
import se.karlminator.cabin_keeper.model.Category;
import se.karlminator.cabin_keeper.model.Product;
import se.karlminator.cabin_keeper.repository.CategoryRepository;
import se.karlminator.cabin_keeper.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository){
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Integer id){
        return categoryRepository.findById(id);
    }

    public Optional<Category> getCategoryByName(String name){
        return categoryRepository.findByName(name);
    }

    public Category saveCategory(Category category){
        return categoryRepository.save(category);
    }

    public void deleteCategory(Integer id){
        categoryRepository.deleteById(id);
    }

    /**
     * Adds a product to a category using the helper methods
     * @return the updated category, or throws exception if not found
     */
    public Category addProductToCategory(Integer categoryId, Integer productId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        // Use the helper method to maintain bidirectional relationship
        category.addProduct(product);

        return categoryRepository.save(category);
    }

    /**
     * Removes a product from a category using the helper methods
     * @return the updated category, or throws exception if not found
     */
    public Category removeProductFromCategory(Integer categoryId, Integer productId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        // Remove product with helper method in category entity.
        category.removeProduct(product);

        return categoryRepository.save(category);
    }

    /**
     * Gets all products in a category, or throws exception if no such category
     * @return list of products in the category
     */

    public List<Product> getProductsInCategory(Integer categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id: "+categoryId));

        return new ArrayList<>(category.getProducts());
    }
}


