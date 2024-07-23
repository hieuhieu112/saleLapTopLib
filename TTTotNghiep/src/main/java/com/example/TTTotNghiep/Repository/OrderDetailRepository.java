package com.example.TTTotNghiep.Repository;

import com.example.TTTotNghiep.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    @Query("SELECT od FROM OrderDetail od WHERE od.order.id = :orderid")
    List<OrderDetail> findByOrderID(@Param("orderid") Integer orderid);
}
