package com.example.TTTotNghiep.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Float discount;
    private LocalDate dateEnd;
    private LocalDate dateStart;
    private LocalDate dateWrite;
    private String reason;
    private Integer type;//0: %, 1:$

    @ManyToOne
    @JoinColumn(name = "employeeID")
    private User creator;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "discount")
    @JsonIgnore
    private List<Discount_Detail> discountDetails;
}
