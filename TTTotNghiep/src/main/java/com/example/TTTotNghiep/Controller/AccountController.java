package com.example.TTTotNghiep.Controller;

import com.example.TTTotNghiep.Response.UserResponse;
import com.example.TTTotNghiep.Service.UserServicesImp;
import com.example.TTTotNghiep.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/account/")
public class AccountController {
    @Autowired
    private UserServicesImp userServices;

    @GetMapping("all")
    public ResponseEntity<List<UserResponse>> getAllCustomer(@RequestHeader("Authorization") String jwt) throws Exception{
        List<UserResponse> responses = new ArrayList<>();
        for(User user: userServices.findAllCustomer()){
            responses.add(user.converToResponse());
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
    //get all employee
}
