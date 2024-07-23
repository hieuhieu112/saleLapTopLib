package com.example.TTTotNghiep.model;


import com.example.TTTotNghiep.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private String name;
    @Column(length = 1000)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> photo;

    private Integer quantity;
    private LocalDate date;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private Integer views;
    private Integer status;



    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnore
    private List<Discount_Detail> discountDetails;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnore
    private List<OrderDetail> orderDetails;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnore
    private List<Price> priceList;

    @ManyToOne
    @JoinColumn(name = "SupllierID")
    private Supplier supplier;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnore
    private List<ReceiptDetail> receiptDetails;

    public ProductDTO convertToDTO(){
        ProductDTO dto = new ProductDTO();
        dto.setId(id);
        dto.setName(getName());
        dto.setPhoto(photo);
        dto.setDate(date);
        dto.setDescription(description);
        dto.setQuantity(quantity);
        dto.setViews(getViews());
        dto.setStatus(status);
        dto.setCategoryID(category.getId());
        dto.setSupplierID(supplier.getId());

        return dto;
    }
}
