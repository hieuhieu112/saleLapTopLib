package com.example.TTTotNghiep.Service;


import com.example.TTTotNghiep.model.User;

import java.util.List;

public interface UserServices {
    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws  Exception;

    public List<User> findAllCustomer() throws  Exception;

    public List<User> findAllEmployee() throws  Exception;
}
