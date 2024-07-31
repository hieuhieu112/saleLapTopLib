package com.example.TTTotNghiep.Response;

import com.example.TTTotNghiep.model.Commune;
import com.example.TTTotNghiep.model.OrderDetail;
import com.example.TTTotNghiep.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Integer id;
    private LocalDateTime orderDate;
    private String receiver;
    private String address;
    private String description;
    private String status; //0: Cancel, 1: Waiting for approve, 2: In process, 3: Done
    private String numberPhone;
    private String orderer;
    private String confirmer;

    private List<OrderDetailResponse> listDetail;
}
