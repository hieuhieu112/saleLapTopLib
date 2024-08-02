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
@AllArgsConstructor
@NoArgsConstructor
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
    @JsonIgnore
    private String keyy;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getNumberphone() {
        return numberphone;
    }

    public void setNumberphone(String numberphone) {
        this.numberphone = numberphone;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddressDescription() {
        return AddressDescription;
    }

    public void setAddressDescription(String addressDescription) {
        AddressDescription = addressDescription;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    public USER_ROLE getCUSTOMERRole() {
        return CUSTOMERRole;
    }

    public void setCUSTOMERRole(USER_ROLE CUSTOMERRole) {
        this.CUSTOMERRole = CUSTOMERRole;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public List<Orders> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Orders> orderList) {
        this.orderList = orderList;
    }

    public List<Orders> getConfirmList() {
        return confirmList;
    }

    public void setConfirmList(List<Orders> confirmList) {
        this.confirmList = confirmList;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public List<Contracts> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contracts> contracts) {
        this.contracts = contracts;
    }

    public List<Integer> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Integer> favorites) {
        this.favorites = favorites;
    }

    public void setPassword(String pass) throws Exception {
        try {
            // Tạo khóa DES
            SecretKey keys = DesEncryptionUtils.generateKey();
            this.keyy = DesEncryptionUtils.keyToString(keys);

            // Mã hóa mật khẩu
            String encryptedPassword = DesEncryptionUtils.encrypt(pass, keys);
            this.password = encryptedPassword;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getPassword() throws Exception{
        try {
            if (this.password != null && this.keyy != null) {
                SecretKey keys = DesEncryptionUtils.stringToKey(this.keyy);
                return DesEncryptionUtils.decrypt(this.password, keys);
            } else {
                throw new Exception("Password or key is not set.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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
