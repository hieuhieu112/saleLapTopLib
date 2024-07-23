package com.example.TTTotNghiep.Repository;

import com.example.TTTotNghiep.model.Product;
import com.example.TTTotNghiep.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt,Integer> {
    @Query("SELECT r FROM Receipt r WHERE r.employee.id = :employeeid and :start < r.dateImport and r.dateImport < :end")
    List<Receipt> findByUserAndTime(@Param("employeeid") Integer employeeid, LocalDateTime start, LocalDateTime end);

    @Query("SELECT r FROM Receipt r WHERE r.supplier.id = :supplierid and :start < r.dateImport and r.dateImport < :end")
    List<Receipt> findBySupplierAndTime(@Param("supplierid") Integer supplierid, LocalDateTime start, LocalDateTime end);
}
