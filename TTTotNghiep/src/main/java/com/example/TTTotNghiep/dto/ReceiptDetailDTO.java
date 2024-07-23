package com.example.TTTotNghiep.dto;


import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ReceiptDetailDTO {
    private Integer quantity;
    private Integer unitprice;
    private Integer productid;
}
