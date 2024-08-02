package com.example.TTTotNghiep.Response;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Data
@Embeddable
public class ProductInSupplierResponse {
    private  Integer id;
    private String name;
    @Column(length = 1000)
    //@ElementCollection
    private List<String> photos;
    private String photo;
    private Integer quantity;
    private LocalDate date;

    private String description;
    private Integer views;
    private Integer status;
    private Integer categoryID;
    private Integer supplierID;

    private Float priceSale;
    private Float pricePurchase;
}
