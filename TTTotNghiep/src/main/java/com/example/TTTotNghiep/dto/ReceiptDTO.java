package com.example.TTTotNghiep.dto;


import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Embeddable
public class ReceiptDTO {
    private LocalDateTime dateImport;
    private String description;
    private String creator;
    private String supplier;

    private List<ReceiptDetailDTO> detailList;
}
