package se.karlminator.cabin_keeper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();

    //constructors
    public Category(){}

    public Category(String name){
        this.name = name;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    // Helper methods
    public void addProduct(Product product) {
        this.products.add(product);
        product.getCategories().add(this);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
        product.getCategories().remove(this);
    }
}
