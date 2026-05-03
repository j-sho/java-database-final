package com.project.code.Controller;

import com.project.code.Model.CombinedRequest;
import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Service.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ServiceClass serviceClass;

    // 3. Define the `updateInventory` Method:
    @PutMapping
    public Map<String, String> updateInventory(@RequestBody CombinedRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            Product product = request.getProduct();
            Inventory inventory = request.getInventory();

            // Validate the product ID
            if (!serviceClass.ValidateProductId(product.getId())) {
                response.put("message", "Product ID is invalid");
                return response;
            }

            // Update the Product
            productRepository.save(product);

            // Update the Inventory
            Inventory existingInventory = inventoryRepository.findByProductIdandStoreId(
                    product.getId(),
                    inventory.getStore().getId()
            );

            if (existingInventory != null) {
                existingInventory.setStockLevel(inventory.getStockLevel());
                inventoryRepository.save(existingInventory);
                response.put("message", "Successfully updated product");
            } else {
                response.put("message", "No data available");
            }
        } catch (DataIntegrityViolationException e) {
            response.put("message", "Data integrity violation: " + e.getMessage());
        } catch (Exception e) {
            response.put("message", "An error occurred: " + e.getMessage());
        }
        return response;
    }

    // 4. Define the `saveInventory` Method:
    @PostMapping
    public Map<String, String> saveInventory(@RequestBody Inventory inventory) {
        Map<String, String> response = new HashMap<>();
        try {
            // Check if inventory already exists
            if (!serviceClass.validateInventory(inventory)) {
                response.put("message", "Data is already present");
                return response;
            }

            inventoryRepository.save(inventory);
            response.put("message", "Data saved successfully");
        } catch (DataIntegrityViolationException e) {
            response.put("message", "Data integrity violation: " + e.getMessage());
        } catch (Exception e) {
            response.put("message", "An error occurred: " + e.getMessage());
        }
        return response;
    }

    // 5. Define the `getAllProducts` Method:
    @GetMapping("/{storeid}")
    public Map<String, Object> getAllProducts(@PathVariable Long storeid) {
        Map<String, Object> response = new HashMap<>();
        List<Product> products = productRepository.findProductsByStoreId(storeid);
        response.put("products", products);
        return response;
    }

    // 6. Define the `getProductName` Method:
    @GetMapping("filter/{category}/{name}/{storeid}")
    public Map<String, Object> getProductName(
            @PathVariable String category,
            @PathVariable String name,
            @PathVariable Long storeid) {
        
        Map<String, Object> response = new HashMap<>();
        List<Product> products;

        if ("null".equals(category)) {
            products = productRepository.findByNameLike(storeid, name);
        } else if ("null".equals(name)) {
            products = productRepository.findByCategoryAndStoreId(storeid, category);
        } else {
            products = productRepository.findByNameAndCategory(storeid, name, category);
        }

        response.put("product", products);
        return response;
    }

    // 7. Define the `searchProduct` Method:
    @GetMapping("search/{name}/{storeId}")
    public Map<String, Object> searchProduct(@PathVariable String name, @PathVariable Long storeId) {
        Map<String, Object> response = new HashMap<>();
        List<Product> products = productRepository.findByNameLike(storeId, name);
        response.put("product", products);
        return response;
    }

    // 8. Define the `removeProduct` Method:
    @DeleteMapping("/{id}")
    public Map<String, String> removeProduct(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        if (!serviceClass.ValidateProductId(id)) {
            response.put("message", "Product not present in database");
            return response;
        }

        inventoryRepository.deleteByProductId(id);
        productRepository.deleteById(id);
        response.put("message", "Product was deleted");
        return response;
    }

    // 9. Define the `validateQuantity` Method:
    @GetMapping("validate/{quantity}/{storeId}/{productId}")
    public boolean validateQuantity(
            @PathVariable Integer quantity,
            @PathVariable Long storeId,
            @PathVariable Long productId) {
        
        Inventory inventory = inventoryRepository.findByProductIdandStoreId(productId, storeId);
        if (inventory != null && inventory.getStockLevel() >= quantity) {
            return true;
        }
        return false;
    }
}
