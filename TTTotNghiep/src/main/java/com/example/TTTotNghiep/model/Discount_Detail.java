package com.example.TTTotNghiep.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Discount_Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "productID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "discountID")
    private Discount discount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discount")
    @JsonIgnore
    private List<OrderDetail> orderDetails;

}
