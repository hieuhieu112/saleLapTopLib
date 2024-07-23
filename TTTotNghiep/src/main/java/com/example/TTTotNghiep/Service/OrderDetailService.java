package com.example.TTTotNghiep.Service;

import com.example.TTTotNghiep.Repository.OrderDetailRepository;
import com.example.TTTotNghiep.model.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> getAllByIDOrder(Integer id) throws Exception{
        return orderDetailRepository.findByOrderID(id);
    }

    public OrderDetail getByID(Integer id) throws Exception{
        Optional<OrderDetail> orderDetail = orderDetailRepository.findById(id);

        if(orderDetail.isEmpty()) throw new Exception("Invalid id OrderDetail");

        return  orderDetail.get();
    }

//    public List<OrderDetail> saveAll(List<Integer> ids) throws Exception{
//        List<OrderDetail> orderDetails = new ArrayList<>();
//
//        for(Integer id: ids){
//             orderDetailRepository.getById(id);
//            orderDetails.add();
//        }
//
//        orderDetailRepository.save()
//    }
}
