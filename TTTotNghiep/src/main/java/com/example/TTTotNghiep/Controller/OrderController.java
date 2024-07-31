package com.example.TTTotNghiep.Controller;


import com.example.TTTotNghiep.Response.OrderResponse;
import com.example.TTTotNghiep.Service.OrderServiceImpl;
import com.example.TTTotNghiep.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getAllOrder(@RequestHeader("Authorization") String jwt) throws Exception{
        List<Orders> orders = orderService.findAll();
        List<OrderResponse> responses = new ArrayList<>();

        for (Orders order:orders){
            responses.add((order.convertToResponse()));
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/time")
    public ResponseEntity<List<OrderResponse>> findByTime(@RequestHeader("Authorization") String jwt,
                                                   @RequestParam LocalDateTime start, @RequestParam LocalDateTime end) throws Exception{
        List<Orders> orders = orderService.getListByTime(start,end);
        List<OrderResponse> responses = new ArrayList<>();

        for (Orders order:orders){
            responses.add((order.convertToResponse()));
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
