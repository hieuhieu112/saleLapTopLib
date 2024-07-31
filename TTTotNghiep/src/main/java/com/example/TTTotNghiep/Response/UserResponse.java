package com.example.TTTotNghiep.Response;

import com.example.TTTotNghiep.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserResponse {
    private Integer id;
    private String email;
    private  String fullname;
    private String numberphone;
    private LocalDate birthday;
    private String gender;
    private Integer status;
    private String address;
    private String photo;
    private String CUSTOMERRole;
    private String department;
    private String employeeType;
    private List<Contracts> contracts;
}
