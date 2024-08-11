package com.example.TTTotNghiep.Controller;

import com.example.TTTotNghiep.Response.MessageResponse;
import com.example.TTTotNghiep.Response.UserResponse;
import com.example.TTTotNghiep.Service.UserServicesImp;
import com.example.TTTotNghiep.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/account/")
public class AccountController {
    @Autowired
    private UserServicesImp userServices;

    @GetMapping("all-customer")
    public ResponseEntity<List<UserResponse>> getAllCustomer(@RequestHeader("Authorization") String jwt) throws Exception{
        List<UserResponse> responses = new ArrayList<>();
        for(User user: userServices.findAllCustomer()){
            responses.add(user.converToResponse());
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("all-employee")
    public ResponseEntity<List<UserResponse>> getAllEmployee(@RequestHeader("Authorization") String jwt) throws Exception{
        List<UserResponse> responses = new ArrayList<>();
        for(User user: userServices.findAllEmployee()){
            responses.add(user.converToResponse());
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getDetailAccount(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws Exception{
        return new ResponseEntity<>(userServices.findByID(id).converToResponse(), HttpStatus.OK);
    }

    @PutMapping("change-status/{id}")
    public ResponseEntity<MessageResponse> changStatusUser(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws  Exception{
        userServices.changeStatus(id);
        return new ResponseEntity<>(new MessageResponse("Chang status OK"),HttpStatus.OK);
    }
}
