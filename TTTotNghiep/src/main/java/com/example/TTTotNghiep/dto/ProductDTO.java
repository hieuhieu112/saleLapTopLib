package com.example.TTTotNghiep.dto;

import com.example.TTTotNghiep.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Embeddable
public class ProductDTO implements Serializable {
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

    private  Float price;
}
