package se.karlminator.cabin_keeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.karlminator.cabin_keeper.model.Category;
import se.karlminator.cabin_keeper.model.Product;
import se.karlminator.cabin_keeper.service.CategoryService;
import se.karlminator.cabin_keeper.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*") // TODO: setup configuration
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public CategoryController(CategoryService categoryService, ProductService productService){
        this.categoryService = categoryService;
        this.productService = productService;
    }

    // GET
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    // POST
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        Category newCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @RequestBody Category category){
        Category updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(updatedCategory);

    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    // Relationship Management

    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getAllProductsFromCategory(@PathVariable Integer id){
        List<Product> products = productService.getProductsByCategoryId(id);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<Category> addProductToCategory(@PathVariable Integer categoryId, @PathVariable Integer productId){
        Category category = categoryService.getCategoryById(categoryId);

        Product product = productService.getProductById(productId);
        product.addCategory(category);
        productService.updateProduct(productId, product);

        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<Void> removeProductFromCategory(@PathVariable Integer categoryId, @PathVariable Integer productId){
        categoryService.removeProductFromCategory(categoryId, productId);
        return ResponseEntity.noContent().build();
    }
}
