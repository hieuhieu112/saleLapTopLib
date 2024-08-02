package com.example.TTTotNghiep.Response;


import com.example.TTTotNghiep.model.Commune;
import com.example.TTTotNghiep.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class SupplierResponse {
    private Integer id;
    private String name;
    private String logo;
    private String address;
    private String email;
    private String numberPhone;

}
