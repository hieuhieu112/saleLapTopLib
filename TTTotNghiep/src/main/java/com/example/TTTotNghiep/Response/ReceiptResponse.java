package com.example.TTTotNghiep.Response;

import com.example.TTTotNghiep.model.ReceiptDetail;
import com.example.TTTotNghiep.model.Supplier;
import com.example.TTTotNghiep.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReceiptResponse {
    private Integer id;
    private String dateImport;
    private String description;
    private String employee;
    private String supplier;
    private List<ReceiptDetailResponse> receiptDetail;
}
