package com.example.TTTotNghiep.Service;

import com.example.TTTotNghiep.Config.JWTProvider;
import com.example.TTTotNghiep.Repository.OrderRepository;
import com.example.TTTotNghiep.Repository.UserRepository;
import com.example.TTTotNghiep.model.OrderDetail;
import com.example.TTTotNghiep.model.Orders;
import com.example.TTTotNghiep.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Orders> findAll() throws Exception{
        return orderRepository.findAll();
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
    public void cartToOrder(String jwt) throws Exception {
        Orders cart = getCart(jwt);
        cart.setType(1);
        orderRepository.save(cart);

        List<OrderDetail> orderDetails = orderDetailService.getAllByIDOrder(cart.getId());
        for(OrderDetail detail: orderDetails){
            productServiceImp.buyOrder(detail.getProduct().getId(), detail.getQuantity());
        }
        createCart(jwt);
    }

    @Override
    public void changeStatus(String jwt, Integer id, Integer status) throws Exception {
        User user = userServices.findUserByJwtToken(jwt);


        Orders order = findByID(id);
        if(!Objects.equals(order.getOrderer().getId(), user.getId())) throw  new Exception("No Access");
        order.setStatus(status);
        orderRepository.save(order);

        if(order.getStatus() == 0) {// cancel order
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

        cart.setStatus(0);
        cart.setOrderDate(null);
        cart.setReceiver(savedUser.getFullname());
        cart.setAddressDescription(savedUser.getAddressDescription());
        cart.setDescription("");
        cart.setNumberPhone(savedUser.getNumberphone());
        cart.setOrderer(savedUser);
        cart.setConfirmer(null);
        cart.setType(0);
        cart.setCommune(savedUser.getCommune());

        return orderRepository.save(cart);
    }


}
