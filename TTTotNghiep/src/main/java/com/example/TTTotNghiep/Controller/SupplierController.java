package com.example.TTTotNghiep.Controller;


import com.example.TTTotNghiep.Request.SupplierRequest;
import com.example.TTTotNghiep.Response.MessageResponse;
import com.example.TTTotNghiep.Service.SuppierServiceImpl;
import com.example.TTTotNghiep.model.Supplier;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/supplier")
public class SupplierController {
    @Autowired
    private SuppierServiceImpl suppierService;


    @GetMapping("/all")
    public ResponseEntity<List<Supplier>> getAll(@RequestHeader("Authorization") String jwt) throws Exception {
        return new ResponseEntity<>(suppierService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getDetail(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws Exception{
        return new ResponseEntity<>(suppierService.findByID(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Supplier> getDetail(@RequestHeader("Authorization") String jwt, @RequestBody SupplierRequest request) throws Exception{
        System.out.println(request.getAddressdescription());
        return new ResponseEntity<>(suppierService.createSupplier(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> editSupplier(@RequestHeader("Authorization") String jwt,
                                                 @PathVariable Integer id, @RequestBody SupplierRequest request) throws Exception{
        return new ResponseEntity<>(suppierService.editSupplier(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteSupplier (@RequestHeader("Authorization") String jwt,
                                                   @PathVariable Integer id) throws Exception{
        suppierService.deleteSupplier(id);
        return new ResponseEntity<>(new MessageResponse("Delete OK"), HttpStatus.OK);
    }
}
