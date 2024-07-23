package com.example.TTTotNghiep.Repository;

import com.example.TTTotNghiep.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Integer> {
    @Query("SELECT p FROM Price p WHERE p.product.id = :productid")
    List<Price> findByProductID(@Param("productid") Integer productid);


    @Query("SELECT p FROM Price p WHERE p.product.id = :productid AND p.start_date < :date AND :date < p.end_date")
    List<Price> findPriceByTimeAndProduct(@Param("productid") Integer productid, @Param("date") LocalDateTime time);
}
