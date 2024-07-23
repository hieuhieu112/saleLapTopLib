package com.example.TTTotNghiep.Request;

import com.example.TTTotNghiep.model.Commune;
import com.example.TTTotNghiep.model.Product;
import com.example.TTTotNghiep.model.Supplier;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class SupplierRequest {
    private String name;
    private String logo;
    private String addressdescription;
    private String email;
    private String numberPhone;
    private String communeID;

    public Supplier convertToModel(Commune commune){
        Supplier supplier = new Supplier();
        supplier.setName(getName());
        supplier.setLogo(getLogo());
        supplier.setEmail(getEmail());
        supplier.setAddressDescription(getAddressdescription());
        supplier.setNumberPhone(getNumberPhone());
        supplier.setCommune(commune);

        return supplier;
    }
}
