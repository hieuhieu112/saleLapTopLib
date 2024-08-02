package com.example.TTTotNghiep.Response;

import com.example.TTTotNghiep.model.Product;
import com.example.TTTotNghiep.model.Receipt;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ReceiptDetailResponse {
    private Integer id;

    private Integer quantity;
    private Float unitprice;
    private String product;
}
