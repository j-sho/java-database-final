package com.project.code.Repo;

import com.project.code.Model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // 2. Add custom query methods:
    //    - **findByProductIdandStoreId**:
    //      - This method will allow you to find an inventory record by its product ID and store ID.
    //      - Return type: Inventory
    //      - Parameters: Long productId, Long storeId
    @Query("SELECT i FROM Inventory i WHERE i.product.id = :productId AND i.store.id = :storeId")
    public Inventory findByProductIdandStoreId(@Param("productId") Long productId, @Param("storeId") Long storeId);

    //    - **findByStore_Id**:
    //      - This method will allow you to find a list of inventory records for a specific store.
    //      - Return type: List<Inventory>
    //      - Parameter: Long storeId
    public List<Inventory> findByStore_Id(Long storeId);

    //    - **deleteByProductId**:
    //      - This method will allow you to delete all inventory records related to a specific product ID.
    //      - Return type: void
    //      - Parameter: Long productId
    //      - Use @Modifying and @Transactional annotations to ensure the database is modified correctly.
    @Modifying
    @Transactional
    @Query("DELETE FROM Inventory i WHERE i.product.id = :productId")
    public void deleteByProductId(@Param("productId") Long productId);
}
