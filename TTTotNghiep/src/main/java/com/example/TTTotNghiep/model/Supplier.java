package com.example.TTTotNghiep.model;

import com.example.TTTotNghiep.Response.SupplierResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String logo;
    private String AddressDescription;
    private String email;
    private String numberPhone;

    @ManyToOne
    @JoinColumn(name = "CommuneID")
    private Commune commune;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplier")
    @JsonIgnore
    private List<Product> products;

    public SupplierResponse convertToResponse(){
        SupplierResponse response = new SupplierResponse();
        response.setId(id);
        response.setName(name);
        response.setLogo(logo);
        response.setEmail(email);
        response.setNumberPhone(numberPhone);

        String add = getAddressDescription()+", "+commune.getName()+", "+commune.getMaqh().getName()+", "+commune.getMaqh().getMatp().getName();
        response.setAddress(add);

        return response;
    }
}
