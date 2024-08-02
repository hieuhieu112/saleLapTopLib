package com.example.TTTotNghiep.Service;



import com.example.TTTotNghiep.Request.OrderRequest;
import com.example.TTTotNghiep.model.OrderDetail;
import com.example.TTTotNghiep.model.Orders;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    public List<Orders>  findAll() throws Exception;
    public List<Orders>  findAllOrder() throws Exception;
    public List<Orders> getListByTime(LocalDateTime start, LocalDateTime end) throws Exception;
    public List<Orders> getListByUser(String jwt) throws Exception;
    public Orders getCart(String jwt) throws Exception;
    public List<Orders> getListOrderByUserStatus(String jwt, Integer status) throws Exception;
    public void cartToOrder(String jwt, OrderRequest request) throws Exception;
    public void changeStatus(String jwt, Integer id, Integer status) throws Exception;
    public Orders createCart(String jwt) throws Exception;
    public List<OrderDetail> getCartItems(String jwt)  throws Exception;
    public Orders approvalOrder(Integer id, Integer status) throws Exception;
}
