package com.example.TTTotNghiep.Service;


import com.example.TTTotNghiep.model.User;

public interface UserServices {
    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws  Exception;
}
