package com.example.TTTotNghiep.model;


import com.example.TTTotNghiep.Response.ReceiptDetailResponse;
import com.example.TTTotNghiep.Response.ReceiptResponse;
import com.example.TTTotNghiep.dto.ReceiptDTO;
import com.example.TTTotNghiep.dto.ReceiptDetailDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private LocalDateTime dateImport;
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "employee_ID")
    private User employee;

    @ManyToOne
    @JoinColumn(name = "supplier_ID")
    private Supplier supplier;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receipt")
    @JsonIgnore
    private List<ReceiptDetail> receiptDetail;


    public ReceiptDTO convertToDTO(List<ReceiptDetailDTO> receiptDetailDTOS){
        ReceiptDTO dto = new ReceiptDTO();
        dto.setDescription(getDescription());
        dto.setSupplier(getSupplier().getName());
        dto.setCreator(getEmployee().getEmail());
        dto.setDateImport(getDateImport());
        dto.setDetailList(receiptDetailDTOS);

        return dto;
    }

    public ReceiptResponse convertToResponse(){
        ReceiptResponse response = new ReceiptResponse();
        response.setId(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = dateImport.format(formatter);
        response.setDateImport(formattedDate);
        response.setDescription(description);
        response.setEmployee(employee.getFullname());
        response.setSupplier(supplier.getName());

        List<ReceiptDetailResponse> responses = new ArrayList<>();
        for(ReceiptDetail detail: receiptDetail){
            responses.add(detail.convertToResponse());
        }
        response.setReceiptDetail(responses);

        return response;
    }
}
