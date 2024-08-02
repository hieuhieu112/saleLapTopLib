package com.example.TTTotNghiep.Request;

import com.example.TTTotNghiep.model.Commune;
import com.example.TTTotNghiep.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderRequest {
    private String receiver;
    private String addressDescription;
    private String description;
    private String numberPhone;
    private String commune;
}
