package com.example.TTTotNghiep.Controller;


import com.example.TTTotNghiep.Response.MessageResponse;
import com.example.TTTotNghiep.Response.OrderDetailResponse;
import com.example.TTTotNghiep.Response.OrderResponse;
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

import java.util.ArrayList;
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
    public ResponseEntity<List<OrderResponse>> getListOrderByUser(@RequestHeader("Authorization") String jwt) throws Exception{
        List<Orders> orders = orderService.getListByUser(jwt);
        List<OrderResponse> responses = new ArrayList<>();

        for(Orders order: orders){
            responses.add(order.convertToResponse());
        }

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderResponse> getDetailOrder(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws Exception{
        Orders order = orderService.findByID(id);
        OrderResponse response = order.convertToResponse();

        List<OrderDetail> detailList = orderDetailService.getAllByIDOrder(id);
        List<OrderDetailResponse> responses = new ArrayList<>();
        for(OrderDetail detail: detailList){
            responses.add(detail.convertToResponse());
        }
        response.setListDetail(responses);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/order/all/{status}")
    public ResponseEntity<List<Orders>> getListByUserStatus(@RequestHeader("Authorization") String jwt, @PathVariable Integer status) throws  Exception{
        return new ResponseEntity<>(orderService.getListOrderByUserStatus(jwt,status), HttpStatus.OK);
    }

    @GetMapping("/order/cart")
    public ResponseEntity<Orders> getCartByUser(@RequestHeader("Authorization") String jwt) throws Exception{
        return new ResponseEntity<>(orderService.getCart(jwt), HttpStatus.OK);
    }

    @GetMapping("/order/cart/detail")
    public ResponseEntity<List<OrderDetailResponse>> getListItemInCart(@RequestHeader("Authorization") String jwt) throws Exception{
        List<OrderDetail> details = orderService.getCartItems(jwt);
        List<OrderDetailResponse> responses = new ArrayList<>();
        for(OrderDetail detail: details){
            responses.add(detail.convertToResponse());
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/order/detail/{id}")
    public ResponseEntity<List<OrderDetailResponse>> getListOrderDetailByIDOrder(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws Exception{
        List<OrderDetail> detailList = orderDetailService.getAllByIDOrder(id);
        List<OrderDetailResponse> responses = new ArrayList<>();
        for(OrderDetail detail: detailList){
            responses.add(detail.convertToResponse());
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
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
