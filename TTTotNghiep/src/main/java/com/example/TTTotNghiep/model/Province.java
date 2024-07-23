package com.example.TTTotNghiep.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Province {
    @Id
    private String matp;
    private String name;
    private String type;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matp")
    @JsonIgnore
    private List<District> districts = new ArrayList<>();
}
