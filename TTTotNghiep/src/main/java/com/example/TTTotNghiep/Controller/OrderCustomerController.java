package com.example.TTTotNghiep.Controller;


import com.example.TTTotNghiep.Response.MessageResponse;
import com.example.TTTotNghiep.Service.OrderDetailService;
import com.example.TTTotNghiep.Service.OrderServiceImpl;
import com.example.TTTotNghiep.Service.ProductServiceImp;
import com.example.TTTotNghiep.Service.UserServicesImp;
import com.example.TTTotNghiep.dto.ProductDTO;
import com.example.TTTotNghiep.model.OrderDetail;
import com.example.TTTotNghiep.model.Orders;
import com.example.TTTotNghiep.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderCustomerController {
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private UserServicesImp userServicesImp;
    @Autowired
    private ProductServiceImp productServiceImp;

    @GetMapping("/order/all")
    public ResponseEntity<List<Orders>> getListOrderByUser(@RequestHeader("Authorization") String jwt) throws Exception{
        return new ResponseEntity<>(orderService.getListByUser(jwt), HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Orders> getDetailOrder(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws Exception{
        return new ResponseEntity<>(orderService.findByID(id), HttpStatus.OK);
    }

    @GetMapping("/order/all/{status}")
    public ResponseEntity<List<Orders>> getListByUserStatus(@RequestHeader("Authorization") String jwt, @PathVariable Integer status) throws  Exception{
        return new ResponseEntity<>(orderService.getListOrderByUserStatus(jwt,status), HttpStatus.OK);
    }

    @GetMapping("/order/cart")
    public ResponseEntity<List<Orders>> getCartByUser(@RequestHeader("Authorization") String jwt) throws Exception{
        return new ResponseEntity<>(orderService.getListByUser(jwt), HttpStatus.OK);
    }

    @GetMapping("/order/detail/{id}")
    public ResponseEntity<List<OrderDetail>> getListOrderDetailByIDOrder(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws Exception{
        return new ResponseEntity<>(orderDetailService.getAllByIDOrder(id), HttpStatus.OK);
    }


    @GetMapping("/product/add-favorite/{id}")
    public ResponseEntity<ProductDTO> addToFavorite(@PathVariable Integer id, @RequestHeader("Authorization") String jwt) throws  Exception{
        User user = userServicesImp.findUserByJwtToken(jwt);

        return new ResponseEntity<>(productServiceImp.addToFavorite(id, user), HttpStatus.OK) ;
    }

    @GetMapping("/product/add-to-cart/{id}")
    public ResponseEntity<MessageResponse> addToCart(@PathVariable Integer id, @RequestHeader("Authorization") String jwt, @RequestParam Integer quantity) throws Exception{
        productServiceImp.changeQuantityFromCard(id, jwt, quantity);

        return new ResponseEntity<>(new MessageResponse("Add to cart success"), HttpStatus.OK);
    }

    @GetMapping("/order/buy")
    public ResponseEntity<MessageResponse> buyProduct(@RequestHeader("Authorization") String jwt) throws Exception{
        orderService.cartToOrder(jwt);
        return new ResponseEntity<>(new MessageResponse("Buy success"), HttpStatus.OK);
    }

    @GetMapping("/order/cancel/{id}")
    public ResponseEntity<MessageResponse> changeStatus(@RequestHeader("Authorization") String jwt,
                                                        @PathVariable Integer id, @RequestParam Integer status) throws Exception{
        orderService.changeStatus(jwt, id, status);
        return new ResponseEntity<>(new MessageResponse("Buy success"), HttpStatus.OK);
    }

}
