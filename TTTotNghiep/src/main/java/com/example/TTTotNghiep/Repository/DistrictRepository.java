package com.example.TTTotNghiep.Repository;

import com.example.TTTotNghiep.model.District;
import com.example.TTTotNghiep.model.Product;
import com.example.TTTotNghiep.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, String> {
    @Query("SELECT d FROM District d WHERE d.matp.id = :tpid")
    public List<District> findByProvince(@Param("tpid") String tpid);
}
