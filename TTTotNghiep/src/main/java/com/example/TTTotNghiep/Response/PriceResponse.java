package com.example.TTTotNghiep.Response;


import com.example.TTTotNghiep.model.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PriceResponse {
    private Integer id;
    private Float price_sale;
    private Float price_purchase;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String product;

}
