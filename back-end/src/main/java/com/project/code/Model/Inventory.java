package com.project.code.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Inventory {

    // 1. Add 'id' field:
    //    - Type: private long 
    //    - This field will represent the unique identifier for the inventory entry.
    //    - Use @Id to mark it as the primary key.
    //    - Use @GeneratedValue(strategy = GenerationType.IDENTITY) to auto-increment it.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2. Add 'product' field:
    //    - Type: private Product
    //    - This field will represent the product associated with the inventory entry.
    //    - Use @ManyToOne to establish a many-to-one relationship with the Product entity.
    // 5. Add relationships:
    //    - **Product Relationship**: Use @ManyToOne to link this inventory entry to a product.
    //    - Use @JsonBackReference("inventory-product") to prevent circular references during JSON serialization for the product field.
    // 6. Use @JoinColumn for foreign key associations:
    //    - For the 'product' field, use @JoinColumn(name = "product_id") to specify the foreign key column.
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference("inventory-product")
    private Product product;

    // 3. Add 'store' field:
    //    - Type: private Store
    //    - This field will represent the store where the inventory is located.
    //    - Use @ManyToOne to establish a many-to-one relationship with the Store entity.
    // 5. Add relationships:
    //    - **Store Relationship**: Use @ManyToOne to link this inventory entry to a store.
    //    - Use @JsonBackReference("inventory-store") to prevent circular references during JSON serialization for the store field.
    // 6. Use @JoinColumn for foreign key associations:
    //    - For the 'store' field, use @JoinColumn(name = "store_id") to specify the foreign key column.
    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonBackReference("inventory-store")
    private Store store;

    // 4. Add 'stockLevel' field:
    //    - Type: private Integer
    //    - This field will represent the current stock level of the product at the store.
    private Integer stockLevel;

    // 7. Create a constructor:
    //    - Add a constructor that takes a Product, Store, and Integer stockLevel to initialize the Inventory object.
    public Inventory() {}

    public Inventory(Product product, Store store, Integer stockLevel) {
        this.product = product;
        this.store = store;
        this.stockLevel = stockLevel;
    }

    // 9. Add Getters and Setters:
    //    - Add getters and setters for 'id', 'product', 'store', and 'stockLevel' fields.
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Integer getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(Integer stockLevel) {
        this.stockLevel = stockLevel;
    }
}
