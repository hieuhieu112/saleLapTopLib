package com.example.TTTotNghiep.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmployeeType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ID;

    private  String NameEmployeeType;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "employeeType")
    @JsonIgnore
    private List<User> users = new ArrayList<>();
}
