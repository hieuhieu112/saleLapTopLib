package com.example.TTTotNghiep.Repository;

import com.example.TTTotNghiep.model.Commune;
import com.example.TTTotNghiep.model.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommuneRepository extends JpaRepository<Commune,String> {
    public List<Commune> findBymaqh(District maqh);
}
