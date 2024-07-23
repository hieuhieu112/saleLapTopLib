package com.example.TTTotNghiep.Service;

import com.example.TTTotNghiep.Request.SupplierRequest;
import com.example.TTTotNghiep.model.Category;
import com.example.TTTotNghiep.model.Supplier;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface SupplierService {
    public Supplier createSupplier(SupplierRequest request) throws Exception;
    public void deleteSupplier(Integer id) throws Exception;
    public Supplier editSupplier(Integer id, SupplierRequest request) throws Exception;
    public List<Supplier> findAll() throws  Exception;
    public Supplier findByID(Integer id) throws  Exception;
}
