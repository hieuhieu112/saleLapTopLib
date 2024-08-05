package com.example.TTTotNghiep.Request;

import com.example.TTTotNghiep.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProductRequest {
    private String name;
    private List<String> photos;
    private String photo;
    private Integer quantity;
    private LocalDate date;
    private String description;
    private Integer categoryID;
    private Integer supplierID;
    private Integer status;

    public Product convertToCreate(Category category, Supplier supplier){
        Product product = new Product();
        product.setName(getName());
        product.setPhoto(getPhoto());
        product.setQuantity(0);
        product.setDescription(getDescription());
        product.setViews(0);
        product.setStatus(getStatus());
        product.setCategory(category);
        product.setSupplier(supplier);


        return product;
    }


    public Product convertToModel(Category category, Supplier supplier){
        Product product = new Product();
        product.setName(getName());
        product.setPhoto(getPhoto());
        product.setQuantity(getQuantity());
        product.setDescription(getDescription());
        product.setViews(0);
        product.setStatus(getStatus());
        product.setCategory(category);
        product.setSupplier(supplier);


        return product;
    }
}
