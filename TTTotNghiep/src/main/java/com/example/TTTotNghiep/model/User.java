package com.example.TTTotNghiep.model;


import com.example.TTTotNghiep.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String email;
    private  String fullname;
    private String numberphone;
    private LocalDate birthday;
    private Integer gender;
    private Integer status;
    private String AddressDescription;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pass;

    private String photo;


//    private String CommuneID;
    @ManyToOne
    @JoinColumn(name = "CommuneID")
    private Commune commune;

//    private Integer roleID;
//    @ManyToOne
//    @JoinColumn(name = "roleID")
//    private Role role;
    private USER_ROLE CUSTOMERRole = USER_ROLE.ROLE_CUSTOMER;


//    private Integer DepartmentID;
    @ManyToOne
    @JoinColumn(name = "DepartmentID")
    private Department department;

//    private Integer EmployeeTypeID;
    @ManyToOne
    @JoinColumn(name = "employee_typeid")
    private EmployeeType employeeType;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    @JsonIgnore
    private List<Discount> discounts;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderer")
    @JsonIgnore
    private List<Orders> orderList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "confirmer")
    @JsonIgnore
    private List<Orders> confirmList;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    @JsonIgnore
    private List<Receipt> receipts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    @JsonIgnore
    private List<Contracts> contracts;

    @ElementCollection //Auto create a table contain data below
    private List<Integer> favorites = new ArrayList<>();
}
