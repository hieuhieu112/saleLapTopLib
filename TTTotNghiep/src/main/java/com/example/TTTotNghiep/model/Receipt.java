package com.example.TTTotNghiep.model;


import com.example.TTTotNghiep.dto.ReceiptDTO;
import com.example.TTTotNghiep.dto.ReceiptDetailDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
}
