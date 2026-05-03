package com.project.code.Controller;

import com.project.code.Model.Customer;
import com.project.code.Model.Review;
import com.project.code.Repo.CustomerRepository;
import com.project.code.Repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // 3. Define the `getReviews` Method:
    @GetMapping("/{storeId}/{productId}")
    public Map<String, Object> getReviews(@PathVariable Long storeId, @PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
        
        // Fetch all reviews for a specific product and store
        List<Review> allReviews = reviewRepository.findByStoreIdAndProductId(storeId, productId);
        
        List<Map<String, Object>> filteredReviews = new ArrayList<>();
        
        for (Review review : allReviews) {
            Map<String, Object> reviewMap = new HashMap<>();
            reviewMap.put("comment", review.getComment());
            reviewMap.put("rating", review.getRating());
            
            // Fetch customer name using customerId
            Customer customer = customerRepository.findById(review.getCustomerId()).orElse(null);
            if (customer != null) {
                reviewMap.put("customerName", customer.getName());
            } else {
                reviewMap.put("customerName", "Unknown");
            }
            
            filteredReviews.add(reviewMap);
        }
        
        response.put("reviews", filteredReviews);
        return response;
    }
}
