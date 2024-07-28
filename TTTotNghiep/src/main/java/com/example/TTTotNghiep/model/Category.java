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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private String photo;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    @JsonIgnore
    private  List<Product> products;
}
