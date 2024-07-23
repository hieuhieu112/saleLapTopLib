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
public class Commune {
    @Id
    private String ID;
    private String name;
    private String type;


//    private String maqh;???
    @ManyToOne
    @JoinColumn(name = "maqh")
    private District maqh;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "commune")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "commune")
    @JsonIgnore
    private List<Supplier> suppliers = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "commune")
    @JsonIgnore
    private List<Orders> orders = new ArrayList<>();
}
