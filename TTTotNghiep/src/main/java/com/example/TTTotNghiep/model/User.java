package com.example.TTTotNghiep.model;


import com.example.TTTotNghiep.Config.DesEncryptionUtils;
import com.example.TTTotNghiep.Response.UserResponse;
import com.example.TTTotNghiep.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import javax.crypto.SecretKey;
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

    public void setPass(String pass) throws Exception {
        try {
            // Tạo khóa DES
            SecretKey key = DesEncryptionUtils.generateKey();

            // Mã hóa mật khẩu
            String encryptedPassword = DesEncryptionUtils.encrypt(pass, key);
            this.pass = encryptedPassword;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public UserResponse converToResponse(){
        UserResponse response = new UserResponse();
        response.setId(id);
        response.setEmail(email);
        response.setFullname(fullname);
        response.setBirthday(getBirthday());
        if(gender == 1){
            response.setGender("Male");
        }else{
            response.setGender("Female");
        }

        response.setStatus(getStatus());
        response.setPhoto(photo);
        response.setNumberphone(numberphone);
        response.setCUSTOMERRole(getCUSTOMERRole().toString());
        if(department != null){
            response.setDepartment(department.getNameDepartment());
        }
        if(contracts != null) response.setContracts(contracts);

        if(employeeType != null )response.setEmployeeType(employeeType.getNameEmployeeType());
        String add = getAddressDescription() +", "+commune.getName()+", "+commune.getMaqh().getName()+", "+commune.getMaqh().getMatp().getName();
        response.setAddress(add);

        return response;
    }
}
