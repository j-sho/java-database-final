package com.project.code.Repo;

import com.project.code.Model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    //    - **findBySubName**:
    //      - This method will retrieve stores whose name contains a given substring.
    //      - Return type: List<Store>
    //      - Parameter: String pname
    //      - Use @Query annotation to write a custom query.
    @Query("SELECT s FROM Store s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :pname, '%'))")
    public List<Store> findBySubName(@Param("pname") String pname);
}
