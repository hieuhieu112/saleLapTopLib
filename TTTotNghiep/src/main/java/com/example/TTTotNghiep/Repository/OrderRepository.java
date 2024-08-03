package com.example.TTTotNghiep.Repository;

import com.example.TTTotNghiep.Response.StatisticalResponse;
import com.example.TTTotNghiep.model.Orders;
import com.example.TTTotNghiep.model.Product;
import com.example.TTTotNghiep.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    @Query("SELECT o FROM Orders o WHERE o.orderer.email = :email AND o.typee = 1")
    List<Orders> findByUserID(@Param("email") String email);

    @Query("SELECT o FROM Orders o WHERE o.typee = 1")
    List<Orders> findAllOrder();

    @Query("SELECT o FROM Orders o WHERE o.orderer.email = :email AND o.typee = 0")
    Orders findCartByUserID(@Param("email") String email);

    @Query("SELECT o FROM Orders o WHERE o.orderer.email = :email AND o.statuss = :statuss")
    List<Orders> findByUserIDStatus(@Param("email") String email, @Param("status") Integer status);

    @Query("SELECT o FROM Orders o WHERE o.orderDate > :start AND o.orderDate < :end")
    List<Orders> findByTime(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


    @Query(name = "StatisticalResponse.getResponseOrder", nativeQuery = true)
    List<StatisticalResponse> getResponseOrder(@Param("start") LocalDate start, @Param("end") LocalDate end);

//    @Query(value = "CALL GetSalesByDate(:start, :end)", nativeQuery = true)
//    List<StatisticalResponse> getResponseOrder(@Param("start") LocalDate start, @Param("end") LocalDate end);

}
