package com.project.code.Repo;

import com.project.code.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // findAll: Find all products.
    public List<Product> findAll();

    // findByCategory: Find products by their category.
    public List<Product> findByCategory(String category);

    // findByPriceBetween: Find products within a price range.
    public List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    // findBySku: Find a product by its SKU.
    public List<Product> findBySku(String sku);

    // findByName: Find a product by its name.
    public Product findByName(String name);

    // findById: Find a product by its ID.
    public Product findById(Long id);

    // findByNameLike: Find products by a name pattern for a specific store.
    @Query("SELECT i.product FROM Inventory i WHERE i.store.id = :storeId AND i.product.category = :category")
    public List<Product> findByNameLike(@Param("storeId") Long storeId, @Param("category") String category);

    // findByNameAndCategory: Find products by name and category for a specific store.
    @Query("SELECT i.product FROM Inventory i WHERE i.store.id = :storeId AND LOWER(i.product.name) LIKE LOWER(CONCAT('%', :pname, '%')) AND i.product.category = :category")
    public List<Product> findByNameAndCategory(@Param("storeId") Long storeId, @Param("pname") String pname, @Param("category") String category);

    // findByCategoryAndStoreId: Find products by category for a specific store.
    @Query("SELECT i.product FROM Inventory i WHERE i.store.id = :storeId AND i.product.category = :category")
    public List<Product> findByCategoryAndStoreId(@Param("storeId") Long storeId, @Param("category") String category);

    // findProductBySubName: Find products by a name pattern (ignoring case).
    @Query("SELECT i FROM Product i WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :pname, '%'))")
    public List<Product> findProductBySubName(@Param("pname") String pname);

    // findProductsByStoreId: Find all products for a specific store.
    @Query("SELECT i.product FROM Inventory i WHERE i.store.id = :storeId")
    public List<Product> findProductsByStoreId(@Param("storeId") Long storeId);

    // findProductByCategory: Find products by category for a specific store.
    @Query("SELECT i.product FROM Inventory i WHERE i.product.category = :category and i.store.id = :storeId")
    public List<Product> findProductByCategory(@Param("category") String category, @Param("storeId") Long storeId);

    // findProductBySubNameAndCategory: Find products by a name pattern and category.
    @Query("SELECT i FROM Product i WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :pname, '%')) AND i.category = :category")
    public List<Product> findProductBySubNameAndCategory(@Param("pname") String pname, @Param("category") String category);
}
