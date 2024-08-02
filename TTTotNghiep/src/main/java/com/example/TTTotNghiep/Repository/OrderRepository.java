package com.example.TTTotNghiep.Repository;

import com.example.TTTotNghiep.model.Orders;
import com.example.TTTotNghiep.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    @Query("SELECT o FROM Orders o WHERE o.orderer.email = :email AND o.type = 1")
    List<Orders> findByUserID(@Param("email") String email);

    @Query("SELECT o FROM Orders o WHERE o.type = 1")
    List<Orders> findAllOrder();

    @Query("SELECT o FROM Orders o WHERE o.orderer.email = :email AND o.type = 0")
    Orders findCartByUserID(@Param("email") String email);

    @Query("SELECT o FROM Orders o WHERE o.orderer.email = :email AND o.status = :status")
    List<Orders> findByUserIDStatus(@Param("email") String email, @Param("status") Integer status);

    @Query("SELECT o FROM Orders o WHERE o.orderDate > :start AND o.orderDate < :end")
    List<Orders> findByTime(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
