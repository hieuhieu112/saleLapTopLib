package com.example.TTTotNghiep.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer quantity;
    private Float unitprice;

    @ManyToOne
    @JoinColumn(name = "DiscountID")
    private Discount_Detail discount;

    @ManyToOne
    @JoinColumn(name = "OrderID")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;
}
