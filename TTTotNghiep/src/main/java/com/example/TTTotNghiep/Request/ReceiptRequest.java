package com.example.TTTotNghiep.Request;

import com.example.TTTotNghiep.model.Receipt;
import com.example.TTTotNghiep.model.ReceiptDetail;
import com.example.TTTotNghiep.model.Supplier;
import com.example.TTTotNghiep.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReceiptRequest {
    private String description;
    private Integer supplierid;

    private List<ReceiptDetailRequest> detailList;


    public Receipt convertToModel(User employee, Supplier supplier){
        Receipt receipt = new Receipt();
        receipt.setEmployee(employee);
        receipt.setSupplier(supplier);
        receipt.setDescription(getDescription());
        receipt.setDateImport(LocalDateTime.now());

        return receipt;
    }
}
