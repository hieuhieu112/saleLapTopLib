package com.example.TTTotNghiep.Service;

import com.example.TTTotNghiep.Config.JWTProvider;
import com.example.TTTotNghiep.Repository.OrderRepository;
import com.example.TTTotNghiep.Repository.UserRepository;
import com.example.TTTotNghiep.Request.OrderRequest;
import com.example.TTTotNghiep.Response.StatisticalResponse;
import com.example.TTTotNghiep.model.OrderDetail;
import com.example.TTTotNghiep.model.Orders;
import com.example.TTTotNghiep.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private  ProductServiceImp productServiceImp;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServicesImp userServices;

    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private CommuneServices communeServices;

    @Override
    public List<Orders> findAll() throws Exception{
        return orderRepository.findAll();
    }

    @Override
    public List<Orders> findAllOrder() throws Exception {
        return orderRepository.findAllOrder();
    }

    public Orders findByID(Integer id) throws Exception{
        Optional<Orders> order = orderRepository.findById(id);
        if(order.isEmpty()){
            throw new Exception("Invalid ID");
        }

        return order.get();
    }

    @Override
    public List<Orders> getListByTime(LocalDateTime start, LocalDateTime end) throws Exception{
        return orderRepository.findByTime(start, end);
    }

    @Override
    public List<Orders> getListByUser(String jwt) throws Exception{
        String email = jwtProvider.getEmailFromJWT(jwt);

        return orderRepository.findByUserID(email);
    }

    @Override
    public Orders getCart(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJWT(jwt);
        return orderRepository.findCartByUserID(email);
    }

    @Override
    public List<Orders> getListOrderByUserStatus(String jwt, Integer status) throws Exception {
        String email = jwtProvider.getEmailFromJWT(jwt);

        return orderRepository.findByUserIDStatus(email,status);
    }

    @Override
    public void cartToOrder(String jwt, OrderRequest request) throws Exception {
        Orders cart = getCart(jwt);
        cart.setTypee(1);
        cart.setStatuss(1);
        cart.setOrderDate(LocalDateTime.now());
        cart.setReceiver(request.getReceiver());
        cart.setDescription(request.getDescription());
        cart.setAddressDescription(request.getAddressDescription());
        cart.setCommune(communeServices.getCommuneByID(request.getCommune()));
        orderRepository.save(cart);

        List<OrderDetail> orderDetails = orderDetailService.getAllByIDOrder(cart.getId());
        for(OrderDetail detail: orderDetails){
            productServiceImp.checkQuantityOrder(detail.getProduct().getId(), detail.getQuantity());
        }
        createCart(jwt);
    }

    @Override
    public void changeStatus(String jwt, Integer id, Integer status) throws Exception {
        User user = userServices.findUserByJwtToken(jwt);


        Orders order = findByID(id);
        if(!Objects.equals(order.getOrderer().getId(), user.getId())) throw  new Exception("No Access");
        if(order.getStatuss() == 1 && (status == 2 || status ==3)){
            List<OrderDetail> orderDetails = orderDetailService.getAllByIDOrder(order.getId());
            for(OrderDetail detail: orderDetails){
                productServiceImp.buyOrder(detail.getProduct().getId(),detail.getQuantity());
            }
        }
        order.setStatuss(status);
        orderRepository.save(order);

        if(order.getStatuss() == 0) {// cancel order
            List<OrderDetail> orderDetails = orderDetailService.getAllByIDOrder(order.getId());
            for(OrderDetail detail: orderDetails){
                productServiceImp.buyOrder(detail.getProduct().getId(),-detail.getQuantity());
            }
        }
    }

    @Override
    public Orders createCart(String jwt) throws Exception {
        User savedUser = userServices.findUserByJwtToken(jwt);
        Orders cart = new Orders();

        cart.setStatuss(0);
        cart.setOrderDate(null);
        cart.setReceiver(savedUser.getFullname());
        cart.setAddressDescription(savedUser.getAddressDescription());
        cart.setDescription("");
        cart.setNumberPhone(savedUser.getNumberphone());
        cart.setOrderer(savedUser);
        cart.setConfirmer(null);
        cart.setTypee(0);
        cart.setCommune(savedUser.getCommune());

        return orderRepository.save(cart);
    }

    public Orders createCartSignUp(String jwt) throws Exception {
        User savedUser = userServices.findUserByJwtTokenSignUp(jwt);
        Orders cart = new Orders();

        cart.setStatuss(0);
        cart.setOrderDate(null);
        cart.setReceiver(savedUser.getFullname());
        cart.setAddressDescription(savedUser.getAddressDescription());
        cart.setDescription("");
        cart.setNumberPhone(savedUser.getNumberphone());
        cart.setOrderer(savedUser);
        cart.setConfirmer(null);
        cart.setTypee(0);
        cart.setCommune(savedUser.getCommune());

        return orderRepository.save(cart);
    }

    @Override
    public List<OrderDetail> getCartItems(String jwt) throws Exception {
        Orders order = getCart(jwt);
        return orderDetailService.getAllByIDOrder(order.getId());
    }

    @Override
    public Orders approvalOrder(Integer id, Integer status) throws Exception {
        Orders orders = findByID(id);
        if(status <= 3 && status >= 0) orders.setStatuss(status);
        orderRepository.save(orders);
        return orders;
    }

    @Override
    public List<StatisticalResponse> getStatistical(LocalDate start, LocalDate end) throws Exception {

        return orderRepository.getResponseOrder(start, end);
    }


}
