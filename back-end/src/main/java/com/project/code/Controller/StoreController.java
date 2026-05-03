package com.project.code.Controller;

import com.project.code.Model.PlaceOrderRequestDTO;
import com.project.code.Model.Store;
import com.project.code.Repo.StoreRepository;
import com.project.code.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderService orderService;

    // 1. **addStore Method**:
    @PostMapping
    public Map<String, String> addStore(@RequestBody Store store) {
        Map<String, String> response = new HashMap<>();
        try {
            Store savedStore = storeRepository.save(store);
            response.put("message", "Store created successfully with ID: " + savedStore.getId());
        } catch (Exception e) {
            response.put("message", "Error creating store: " + e.getMessage());
        }
        return response;
    }

    // 2. **validateStore Method**:
    //    - Exposes a GET endpoint to validate store existence.
    //    - Endpoint: GET /store/validate/store/{id}
    @GetMapping("/validate/store/{id}")
    public boolean validateStore(@PathVariable Long id) {
        // Checks for store existence in the database
        return storeRepository.findById(id).isPresent();
    }

    // 3. **placeOrder Method**:
    //    - Processes an order and handles exceptions using a try-catch block.
    @PostMapping("/placeOrder")
    public Map<String, String> placeOrder(@RequestBody PlaceOrderRequestDTO placeOrderRequest) {
        Map<String, String> response = new HashMap<>();
        // Exception handling using try-catch block
        try {
            orderService.saveOrder(placeOrderRequest);
            response.put("message", "Order placed successfully");
        } catch (Exception e) {
            // Return error message in case of failure
            response.put("message", "Order placement failed: " + e.getMessage());
        }
        return response;
    }
}
