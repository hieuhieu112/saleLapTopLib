package com.example.TTTotNghiep.Request;

import com.example.TTTotNghiep.dto.ProductDTO;
import com.example.TTTotNghiep.model.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserRequest {

    private String email;
    private String pass;
    private String fullname;
    private String numberphone;
    private Date birthday;
    private Integer gender;
    private String address_description;
    private String photo;
    private String commune;
    private Integer department;
    private Integer employeeType;
    private  List<ProductDTO> favorites;

    public User convertToUser(Commune commune) throws Exception {
       User user = new User();
       user.setEmail(email);
       user.setPass(pass);
       user.setFullname(getFullname());
       user.setNumberphone(getNumberphone());
       user.setBirthday(null);
       user.setGender(getGender());
       user.setStatus(0);
       user.setAddressDescription(getAddress_description());
       user.setPhoto(getPhoto());
       user.setCommune(commune);
       user.setCUSTOMERRole(USER_ROLE.valueOf("ROLE_CUSTOMER"));
       user.setDepartment(null);
       user.setEmployeeType(null);
        return user;
    }
}
