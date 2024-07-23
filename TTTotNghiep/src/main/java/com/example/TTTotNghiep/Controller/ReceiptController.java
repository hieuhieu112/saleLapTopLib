package com.example.TTTotNghiep.Controller;


import com.example.TTTotNghiep.Request.ReceiptRequest;
import com.example.TTTotNghiep.Response.MessageResponse;
import com.example.TTTotNghiep.Service.ReceiptServiceImp;
import com.example.TTTotNghiep.model.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/receipt")
public class ReceiptController {
    @Autowired
    private ReceiptServiceImp receiptService;

    @GetMapping("/all")
    public ResponseEntity<List<Receipt>>  getALl(@RequestHeader("Authorization") String jwt) throws Exception {
        return new ResponseEntity<>(receiptService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receipt> getByID(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws Exception{
        return new ResponseEntity<>(receiptService.findByID(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteByID(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws  Exception{
        receiptService.deleteByID(id);
        return new ResponseEntity<>(new MessageResponse("Delete Receipt OK"), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Receipt> createReceipt(@RequestHeader("Authorization") String jwt,@RequestBody ReceiptRequest request) throws Exception{
        return new ResponseEntity<>(receiptService.createReceipt(request, jwt), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Receipt> editReceipt(@RequestHeader("Authorization") String jwt,
                                               @RequestBody ReceiptRequest request, @PathVariable Integer id) throws Exception{
        return new ResponseEntity<>(receiptService.editReceipt(id, request, jwt), HttpStatus.OK);
    }

    @GetMapping("/user/created")
    public ResponseEntity<List<Receipt>> getListByUserTime(@RequestHeader("Authorization") String jwt,
                                                           @RequestBody LocalDateTime start, @RequestBody LocalDateTime end) throws  Exception{
        return new ResponseEntity<>(receiptService.getByUserAndTime(jwt, start, end), HttpStatus.OK);
    }

    @GetMapping("/supplier/{id}/created")
    public ResponseEntity<List<Receipt>> getListBySupplier(@RequestHeader("Authorization") String jwt, @PathVariable Integer id,
                                                           @RequestBody LocalDateTime start, @RequestBody LocalDateTime end) throws Exception{
        return new ResponseEntity<>(receiptService.getBySupplierAndTime(id, start, end), HttpStatus.OK);
    }
}
