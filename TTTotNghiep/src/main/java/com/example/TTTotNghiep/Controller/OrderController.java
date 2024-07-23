package com.example.TTTotNghiep.Controller;


import com.example.TTTotNghiep.Service.OrderServiceImpl;
import com.example.TTTotNghiep.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("/all")
    public ResponseEntity<List<Orders>> getAllOrder(@RequestHeader("Authorization") String jwt) throws Exception{
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/time")
    public ResponseEntity<List<Orders>> findByTime(@RequestHeader("Authorization") String jwt,
                                                   @RequestParam LocalDateTime start, @RequestParam LocalDateTime end) throws Exception{
        return new ResponseEntity<>(orderService.getListByTime(start,end), HttpStatus.OK);
    }
}
