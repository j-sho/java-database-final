package com.project.code.Service;

import com.project.code.Model.PlaceOrderRequestDTO;
import com.project.code.Model.PurchaseProductDTO;
import com.project.code.Model.Customer;
import com.project.code.Model.OrderDetails;
import com.project.code.Model.OrderItem;
import com.project.code.Model.Product;
import com.project.code.Model.Store;
import com.project.code.Model.Inventory;
import com.project.code.Repo.CustomerRepository;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.OrderDetailsRepository;
import com.project.code.Repo.OrderItemRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Repo.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // 1. **saveOrder Method**:
    //    - Processes a customer's order, including saving the order details and associated items.
    //    - Parameters: `PlaceOrderRequestDTO placeOrderRequest` (Request data for placing an order)
    //    - Return Type: `void` (This method doesn't return anything, it just processes the order)
    @Transactional
    public void saveOrder(PlaceOrderRequestDTO placeOrderRequest) {
        
        // 2. **Retrieve or Create the Customer**:
        Customer customer = customerRepository.findByEmail(placeOrderRequest.getCustomerEmail());
        if (customer == null) {
            customer = new Customer();
            customer.setEmail(placeOrderRequest.getCustomerEmail());
            customer.setName(placeOrderRequest.getCustomerName());
            customer.setPhone(placeOrderRequest.getCustomerPhone());
            customer = customerRepository.save(customer);
        }

        // 3. **Retrieve the Store**:
        Store store = storeRepository.findById(placeOrderRequest.getStoreId()).orElse(null);
        if (store == null) {
            throw new RuntimeException("Store not found with id: " + placeOrderRequest.getStoreId());
        }

        // 4. **Create OrderDetails**:
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCustomer(customer);
        orderDetails.setStore(store);
        orderDetails.setTotalPrice(placeOrderRequest.getTotalPrice());
        orderDetails.setDate(LocalDateTime.now());
        // Save the OrderDetails record
        orderDetails = orderDetailsRepository.save(orderDetails);

        // 5. **Create and Save OrderItems and Update Inventory**:
        if (placeOrderRequest.getPurchaseProduct() != null) {
            for (PurchaseProductDTO purchaseProduct : placeOrderRequest.getPurchaseProduct()) {
                
                // Reduce inventory stock level
                Inventory inventory = inventoryRepository.findByProductIdandStoreId(
                    purchaseProduct.getId(), 
                    store.getId()
                );
                
                if (inventory != null) {
                    int updatedStock = inventory.getStockLevel() - purchaseProduct.getQuantity();
                    inventory.setStockLevel(updatedStock);
                    // Save the updated inventory
                    inventoryRepository.save(inventory);
                }

                // Create and Save OrderItem
                Product product = productRepository.findById(purchaseProduct.getId()).orElse(null);
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(orderDetails);
                orderItem.setProduct(product);
                orderItem.setQuantity(purchaseProduct.getQuantity());
                orderItem.setPrice(purchaseProduct.getPrice());
                
                orderItemRepository.save(orderItem);
            }
        }
    }
}
