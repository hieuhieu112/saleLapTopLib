package com.example.TTTotNghiep.dto;


import lombok.Data;
import lombok.Setter;

import java.io.Serializable;

@Data
public class PaymentResDTO implements Serializable {
    private  String message;
    private String URL;
    private String status;
}
