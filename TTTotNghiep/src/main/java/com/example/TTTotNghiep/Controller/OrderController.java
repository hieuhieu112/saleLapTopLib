package com.example.TTTotNghiep.Controller;


import com.example.TTTotNghiep.Response.OrderResponse;
import com.example.TTTotNghiep.Response.StatisticalResponse;
import com.example.TTTotNghiep.Service.OrderServiceImpl;
import com.example.TTTotNghiep.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/admin/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getAllOrder(@RequestHeader("Authorization") String jwt) throws Exception{
        List<Orders> orders = orderService.findAllOrder();
        List<OrderResponse> responses = new ArrayList<>();

        for (Orders order:orders){
            responses.add((order.convertToResponse()));
        }
        Collections.reverse(responses);
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

    @GetMapping("/approval/{id}")
    public ResponseEntity<OrderResponse> approvalOrder(@RequestHeader("Authorization") String jwt,
                                                       @PathVariable Integer id, @RequestParam Integer status) throws  Exception{

        return new ResponseEntity<>(orderService.approvalOrder(id, status).convertToResponse(), HttpStatus.OK);
    }
    @GetMapping("/statistical")
    public ResponseEntity<List<StatisticalResponse>> getOrderStatistical(@RequestHeader("Authorization") String jwt, @RequestParam LocalDate start,
                                                                         @RequestParam LocalDate end) throws  Exception{

        return new ResponseEntity<>(orderService.getStatistical(start,end), HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<OrderResponse>>  findByCustomer(@RequestHeader("Authorization") String jwt,@PathVariable Integer id) throws Exception{
        List<OrderResponse> responseList = new ArrayList<>();
        List<Orders> orders = orderService.getByUser(id);

        for(Orders order: orders){
            responseList.add(order.convertToResponse());
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
