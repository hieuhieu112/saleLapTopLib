package com.example.TTTotNghiep.Repository;


import com.example.TTTotNghiep.model.Product;
import com.example.TTTotNghiep.model.USER_ROLE;
import com.example.TTTotNghiep.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);


    @Query(value = "CALL getUser(:type)", nativeQuery = true)
    List<User> findAllUserByType(@Param("type") Integer type);
}
