package com.example.TTTotNghiep.Request;

import com.example.TTTotNghiep.model.Receipt;
import com.example.TTTotNghiep.model.ReceiptDetail;
import com.example.TTTotNghiep.model.Supplier;
import com.example.TTTotNghiep.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReceiptRequest {

    private LocalDateTime dateImport;
    private String description;
//    private String employeeid;
    private Integer supplierid;

    private List<ReceiptDetailRequest> detailList;


    public Receipt convertToModel(User employee, Supplier supplier){
        Receipt receipt = new Receipt();
        receipt.setEmployee(employee);
        receipt.setSupplier(supplier);
        receipt.setDescription(getDescription());
        receipt.setDateImport(getDateImport());

        return receipt;
    }
}
