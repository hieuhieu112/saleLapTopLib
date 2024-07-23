package com.example.TTTotNghiep.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Orders {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer id;

    private LocalDateTime orderDate;
    private String receiver;
    private String addressDescription;
    private String description;
    private Integer status; //0: Cancel, 1: Waiting for approve, 2: In process, 3: Done
    private String numberPhone;
    private Integer type;//1: order, 0: cart


    @ManyToOne
    @JoinColumn(name = "CommuneID")
    private Commune commune;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private User orderer;

    @ManyToOne
    @JoinColumn(name = "EmployeeID")
    private User confirmer;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @JsonIgnore
    private List<OrderDetail>  orderDetails;


}
