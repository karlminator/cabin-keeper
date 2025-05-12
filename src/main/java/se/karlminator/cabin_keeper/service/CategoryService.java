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

    public List<Category> getAllCategoriesWithProducts(){
        return categoryRepository.findAllWithProducts();
    }

    public Category getCategoryByIdWithProducts(Integer id){
        return categoryRepository.findByIdWithProducts(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id: "+id));
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Integer id){
        return categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id: "+id));
    }

    public Category createCategory(Category category){
        category.setId(null);
        return categoryRepository.save(category);
    }

    public Category updateCategory(Integer id, Category categoryDetails){
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id: " + id));

        existingCategory.setName(categoryDetails.getName());
        return categoryRepository.save(existingCategory);
    }

    public boolean deleteCategory(Integer id){
        if (categoryRepository.existsById(id)) {
        categoryRepository.deleteById(id);
        return true;
        }
        return false;
    }

    public void addProductToCategory(Integer categoryId, Integer productId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        product.addCategory(category);
        productRepository.save(product);
    }

    public void removeProductFromCategory(Integer categoryId, Integer productId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        // Remove product with helper method in category entity.
        category.removeProduct(product);
        product.removeCategory(category);

        // As Product owns this relation, we will save from the owning side.
        // return categoryRepository.save(category); -- Old code ❌
        productRepository.save(product); // -- New code ✅
    }
}


