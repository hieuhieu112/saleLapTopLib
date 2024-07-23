package com.example.TTTotNghiep.Repository;

import com.example.TTTotNghiep.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, CustomProductRepository {
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryid")
    List<Product> findByCategoryID(@Param("categoryid") Integer categoryid);

    @Query("SELECT p FROM Product p WHERE lower(p.name) LIKE lower(concat('%',:search,'%')) OR lower(p.description) LIKE lower(concat('%',:search,'%')) ")
    List<Product> searchProduct(@Param("search") String search);

    @Query(value = "CALL delete_product_photo(:id);", nativeQuery = true)
    void deleteProductPhoto(@Param("id") Integer id);
}
