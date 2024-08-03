package com.example.TTTotNghiep.Controller;


import com.example.TTTotNghiep.Request.ReceiptRequest;
import com.example.TTTotNghiep.Response.MessageResponse;
import com.example.TTTotNghiep.Response.ReceiptResponse;
import com.example.TTTotNghiep.Service.ReceiptServiceImp;
import com.example.TTTotNghiep.model.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/admin/receipt")
public class ReceiptController {
    @Autowired
    private ReceiptServiceImp receiptService;

    @GetMapping("/all")
    public ResponseEntity<List<ReceiptResponse>>  getALl(@RequestHeader("Authorization") String jwt) throws Exception {
        List<Receipt> receipts = receiptService.getAll();
        List<ReceiptResponse> responses = new ArrayList<>();
        for(Receipt receipt:receipts){
            responses.add(receipt.convertToResponse());
        }
        Collections.reverse(responses);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceiptResponse> getByID(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws Exception{
        return new ResponseEntity<>(receiptService.findByID(id).convertToResponse(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteByID(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws  Exception{
        receiptService.deleteByID(id);
        return new ResponseEntity<>(new MessageResponse("Delete Receipt OK"), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ReceiptResponse> createReceipt(@RequestHeader("Authorization") String jwt,@RequestBody ReceiptRequest request) throws Exception{
        Receipt receipt = receiptService.createReceipt(request, jwt);
        return new ResponseEntity<>(receipt.convertToResponse(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceiptResponse> editReceipt(@RequestHeader("Authorization") String jwt,
                                               @RequestBody ReceiptRequest request, @PathVariable Integer id) throws Exception{
        return new ResponseEntity<>(receiptService.editReceipt(id, request, jwt).convertToResponse(), HttpStatus.OK);
    }

    @GetMapping("/user/created")
    public ResponseEntity<List<ReceiptResponse>> getListByUserTime(@RequestHeader("Authorization") String jwt,
                                                           @RequestBody LocalDateTime start, @RequestBody LocalDateTime end) throws  Exception{
        List<Receipt> receipts = receiptService.getByUserAndTime(jwt, start, end);
        List<ReceiptResponse> responses = new ArrayList<>();
        for(Receipt receipt:receipts){
            responses.add(receipt.convertToResponse());
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/supplier/{id}/created")
    public ResponseEntity<List<ReceiptResponse>> getListBySupplier(@RequestHeader("Authorization") String jwt, @PathVariable Integer id,
                                                           @RequestBody LocalDateTime start, @RequestBody LocalDateTime end) throws Exception{

        List<Receipt> receipts = receiptService.getBySupplierAndTime(id, start, end);
        List<ReceiptResponse> responses = new ArrayList<>();
        for(Receipt receipt:receipts){
            responses.add(receipt.convertToResponse());
        }

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
