package com.example.TTTotNghiep.Controller;


import com.example.TTTotNghiep.Config.JWTProvider;
import com.example.TTTotNghiep.Repository.CommuneRepository;
import com.example.TTTotNghiep.Repository.UserRepository;
import com.example.TTTotNghiep.Request.UserRequest;
import com.example.TTTotNghiep.Response.AuthResponse;
import com.example.TTTotNghiep.Response.OrderResponse;
import com.example.TTTotNghiep.Service.OrderServiceImpl;
import com.example.TTTotNghiep.model.Orders;
import com.example.TTTotNghiep.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/employee")
public class EmployeeController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private OrderServiceImpl orderService;


    @PostMapping("/create-employee")
    public ResponseEntity<AuthResponse> createHandleEmployee(@RequestHeader("Authorization") String jwt, @RequestBody UserRequest userrequest) throws Exception {
        User user = userrequest.convertToEmployee(communeRepository.getReferenceById(userrequest.getCommune()));
        User isEmailExist = userRepository.findByEmail(user.getEmail());

        if(isEmailExist!= null){
            throw new Exception("Email is already used with another account");
        }

        User createUser = new User();
        createUser.setEmail(user.getEmail());
//        createUser.setPass(passwordEncoder.encode(user.getPass()));
        createUser.setPassword(userrequest.getPass());
        createUser.setBirthday(user.getBirthday());
        createUser.setCommune(user.getCommune());
        createUser.setFullname(user.getFullname());
        createUser.setPhoto(user.getPhoto());
        //photo?
        createUser.setNumberphone(user.getNumberphone());
        createUser.setGender(user.getGender());
        createUser.setStatus(0);
        createUser.setCUSTOMERRole(user.getCUSTOMERRole());
        //createUser.setDepartment(1);
        //createUser.setEmployeeType(1);
        //createUser.setCommune(00001);
        createUser.setAddressDescription(user.getAddressDescription());

        User savedUser = userRepository.save(createUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtGen = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwtGen);
        authResponse.setRole(createUser.getCUSTOMERRole());
        authResponse.setMessage("Sign up Employee success");
        orderService.createCartSignUp(jwtGen);


        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getAllOrder(@RequestHeader("Authorization") String jwt) throws Exception{
        List<Orders> orders = orderService.findAllOrder();
        List<OrderResponse> responses = new ArrayList<>();

        for (Orders order:orders){
            responses.add((order.convertToResponse()));
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }



}
