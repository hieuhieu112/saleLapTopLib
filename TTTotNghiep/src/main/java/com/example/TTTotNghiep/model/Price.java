package com.example.TTTotNghiep.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Float price_sale;
    private Float price_purchase;

    private LocalDateTime start_date;
    private LocalDateTime end_date;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
