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
public class District {
    @Id
    private String maqh;
    private String name;
    private String type;

//    private String matp;???
    @ManyToOne
    @JoinColumn(name = "matp")
    private Province matp;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maqh")
    @JsonIgnore
    private List<Commune> communes = new ArrayList<>();

}
