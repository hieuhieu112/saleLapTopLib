package com.example.TTTotNghiep.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Contracts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDate endtime;
    private Float insurance;
    private  String namecontract;
    private Integer salary;
    private LocalDate starttime;
    private Integer status;
    private Integer subsidize;


    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employee;


}
