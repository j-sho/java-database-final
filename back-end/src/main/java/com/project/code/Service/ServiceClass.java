package com.project.code.Service;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceClass {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ServiceClass(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    // 1. **validateInventory Method**:
    //    - Checks if an inventory record exists for a given product and store combination.
    //    - Parameters: `Inventory inventory`
    //    - Return Type: `boolean` (Returns `false` if inventory exists, otherwise `true`)
    public boolean validateInventory(Inventory inventory) {
        Long productId = inventory.getProduct().getId();
        Long storeId = inventory.getStore().getId();
        
        Inventory existingInventory = inventoryRepository.findByProductIdandStoreId(productId, storeId);
        
        // Returns false if an inventory record already exists, otherwise true
        return existingInventory == null;
    }

    // 2. **validateProduct Method**:
    //    - Checks if a product exists by its name.
    //    - Parameters: `Product product`
    //    - Return Type: `boolean` (Returns `false` if a product with the same name exists, otherwise `true`)
    public boolean validateProduct(Product product) {
        Product existingProduct = productRepository.findByName(product.getName());
        // Returns false if a product with the same name already exists, otherwise true
        return existingProduct == null;
    }

    // 3. **ValidateProductId Method**:
    //    - Checks if a product exists by its ID.
    //    - Parameters: `long id`
    //    - Return Type: `boolean` (Returns `false` if the product does not exist with the given ID, otherwise `true`)
    public boolean ValidateProductId(long id) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        // Returns false if the product does not exist, otherwise true
        return existingProduct != null;
    }

    // 4. **getInventoryId Method**:
    //    - Fetches the inventory record for a given product and store combination.
    //    - Parameters: `Inventory inventory`
    //    - Return Type: `Inventory` (Returns the inventory record for the product-store combination)
    public Inventory getInventoryId(Inventory inventory) {
        Long productId = inventory.getProduct().getId();
        Long storeId = inventory.getStore().getId();
        
        return inventoryRepository.findByProductIdandStoreId(productId, storeId);
    }
}
