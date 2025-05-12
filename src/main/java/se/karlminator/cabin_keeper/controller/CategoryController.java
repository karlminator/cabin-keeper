package se.karlminator.cabin_keeper.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.karlminator.cabin_keeper.dto.CategoryDTO;
import se.karlminator.cabin_keeper.mapper.CategoryMapper;
import se.karlminator.cabin_keeper.mapper.ProductMapper;
import se.karlminator.cabin_keeper.model.Category;
import se.karlminator.cabin_keeper.model.Product;
import se.karlminator.cabin_keeper.service.CategoryService;
import se.karlminator.cabin_keeper.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*") // TODO: setup configuration
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ProductService productService,
                              CategoryMapper categoryMapper, ProductMapper productMapper){
        this.categoryService = categoryService;
        this.productService = productService;
        this.categoryMapper = categoryMapper;
        this.productMapper = productMapper;
    }

    // GET
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        List<Category> categories = categoryService.getAllCategoriesWithProducts();
        List<CategoryDTO> categoriesDTOs = categories.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoriesDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer id){
        Category category = categoryService.getCategoryByIdWithProducts(id);
        return ResponseEntity.ok(categoryMapper.toDto(category));
    }

    // POST
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO){
        Category category = categoryMapper.toEntity(categoryDTO);
        Category newCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.toDto(newCategory));
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Integer id, @Valid @RequestBody CategoryDTO categoryDTO){
        Category category = categoryMapper.toEntity(categoryDTO);
        Category updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(categoryMapper.toDto(updatedCategory));

    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id){
       boolean success = categoryService.deleteCategory(id);

       if (success){
           return ResponseEntity.noContent().build();
       }else{
           return ResponseEntity.notFound().build();
       }
    }

    // Relationship Management

    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getAllProductsFromCategory(@PathVariable Integer id){
        List<Product> products = productService.getProductsByCategoryId(id);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<Void> addProductToCategory(@PathVariable Integer categoryId, @PathVariable Integer productId) {
        categoryService.addProductToCategory(categoryId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<Void> removeProductFromCategory(@PathVariable Integer categoryId, @PathVariable Integer productId){
        categoryService.removeProductFromCategory(categoryId, productId);
        return ResponseEntity.noContent().build();
    }
}
