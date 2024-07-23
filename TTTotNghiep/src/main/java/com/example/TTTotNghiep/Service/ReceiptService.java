package com.example.TTTotNghiep.Service;

import com.example.TTTotNghiep.Request.ReceiptRequest;
import com.example.TTTotNghiep.model.Receipt;

import java.time.LocalDateTime;
import java.util.List;

public interface ReceiptService {
    public List<Receipt> getAll() throws  Exception;
    public List<Receipt> getByUserAndTime(String jwt, LocalDateTime start, LocalDateTime end) throws  Exception;
    public List<Receipt> getBySupplierAndTime(Integer id, LocalDateTime start, LocalDateTime end) throws Exception;

    public Receipt createReceipt(ReceiptRequest receipt, String jwt) throws Exception;
    public Receipt findByID(Integer id) throws Exception;
    public void deleteByID(Integer id) throws Exception;
    public Receipt editReceipt(Integer id, ReceiptRequest request, String jwt) throws Exception;
}
