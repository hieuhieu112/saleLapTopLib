package com.example.TTTotNghiep.Response;


import com.example.TTTotNghiep.model.Discount_Detail;
import com.example.TTTotNghiep.model.Orders;
import com.example.TTTotNghiep.model.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class OrderDetailResponse {
    private Integer id;
    private Integer quantity;
    private Float unitprice;
    private Float discount;
    private String product;
    private String photo;
}
