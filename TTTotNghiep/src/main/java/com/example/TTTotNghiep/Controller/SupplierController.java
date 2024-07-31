package com.example.TTTotNghiep.Controller;


import com.example.TTTotNghiep.Request.SupplierRequest;
import com.example.TTTotNghiep.Response.MessageResponse;
import com.example.TTTotNghiep.Response.SupplierResponse;
import com.example.TTTotNghiep.Service.SuppierServiceImpl;
import com.example.TTTotNghiep.model.Supplier;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/supplier")
public class SupplierController {
    @Autowired
    private SuppierServiceImpl suppierService;


    @GetMapping("/all")
    public ResponseEntity<List<SupplierResponse>> getAll(@RequestHeader("Authorization") String jwt) throws Exception {
        List<Supplier> suppliers = suppierService.findAll();
        List<SupplierResponse> responses = new ArrayList<>();
        for (Supplier supplier:suppliers){
            responses.add((supplier.convertToResponse()));
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponse> getDetail(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws Exception{
        Supplier supplier = suppierService.findByID(id);
        return new ResponseEntity<>(supplier.convertToResponse(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<SupplierResponse> getDetail(@RequestHeader("Authorization") String jwt, @RequestBody SupplierRequest request) throws Exception{
        return new ResponseEntity<>(suppierService.createSupplier(request).convertToResponse(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponse> editSupplier(@RequestHeader("Authorization") String jwt,
                                                 @PathVariable Integer id, @RequestBody SupplierRequest request) throws Exception{
        return new ResponseEntity<>(suppierService.editSupplier(id, request).convertToResponse(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteSupplier (@RequestHeader("Authorization") String jwt,
                                                   @PathVariable Integer id) throws Exception{
        suppierService.deleteSupplier(id);
        return new ResponseEntity<>(new MessageResponse("Delete OK"), HttpStatus.OK);
    }
}
