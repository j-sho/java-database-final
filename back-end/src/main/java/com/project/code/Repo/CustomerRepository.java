package com.project.code.Repo;

import com.project.code.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // 2. Add custom query methods:
    //    - **findByEmail**:
    //      - This method will allow you to find a customer by their email address.
    //      - Return type: Customer
    //      - Parameter: String email
    public Customer findByEmail(String email);

    //    - **findById**:
    //      - This method will allow you to find a customer by their ID.
    //      - Return type: Customer
    //      - Parameter: Long id
    // Note: JpaRepository already provides Optional<Customer> findById(Long id). 
    // This custom method returns Customer directly.
    public Customer findById(Long id);

    // 3. Add any additional methods you may need for custom queries:
    // Example: public List<Customer> findByName(String name);
}
