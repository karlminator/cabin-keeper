package se.karlminator.cabin_keeper.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import se.karlminator.cabin_keeper.dto.ProductDTO;
import se.karlminator.cabin_keeper.error.ErrorResponse;
import se.karlminator.cabin_keeper.mapper.ProductMapper;
import se.karlminator.cabin_keeper.model.Product;
import se.karlminator.cabin_keeper.service.CategoryService;
import se.karlminator.cabin_keeper.service.ProductService;
import se.karlminator.cabin_keeper.service.RoomService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*") // TODO: setup configuration
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    // GET
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProductsWithDetails();
        List<ProductDTO> productDTOs = products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id){
        Product product = productService.getProductByIdWithDetails(id);
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    // POST
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO){
        Product product = productMapper.toEntity(productDTO);
        Product newProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.toDto(newProduct));
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer id, @Valid @RequestBody ProductDTO productDTO){
        Product product = productMapper.toEntity(productDTO);
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(productMapper.toDto(updatedProduct));
    }

    // PATCH (partially updating)
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductDTO> updateStock(@PathVariable Integer id, @RequestParam Integer quantity){
        Product updatedProduct = productService.updateStock(id, quantity);
        return ResponseEntity.ok(productMapper.toDto(updatedProduct));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id){
        boolean success = productService.deleteProduct(id);
        if (success){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // Relationship Endpoints

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Product>> getProductsByRoom(@PathVariable Integer roomId){
        List<Product> products = productService.getProductsByRoomId(roomId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Integer categoryId){
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }
}
