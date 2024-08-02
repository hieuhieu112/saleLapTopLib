package com.example.TTTotNghiep.Controller;


import com.example.TTTotNghiep.Request.ReceiptRequest;
import com.example.TTTotNghiep.Response.OrderResponse;
import com.example.TTTotNghiep.Response.ProductInSupplierResponse;
import com.example.TTTotNghiep.Response.ReceiptResponse;
import com.example.TTTotNghiep.Response.SupplierResponse;
import com.example.TTTotNghiep.Service.*;
import com.example.TTTotNghiep.dto.ProductDTO;
import com.example.TTTotNghiep.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeUserController {
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private SuppierServiceImpl suppierService;
    @Autowired
    private ReceiptServiceImp receiptService;
    @Autowired
    private ProductServiceImp productServiceImp;
    @Autowired
    private PriceServicesImpl priceServices;


    @GetMapping("/order/all")
    public ResponseEntity<List<OrderResponse>> getAllOrder(@RequestHeader("Authorization") String jwt) throws Exception{
        List<Orders> orders = orderService.findAllOrder();
        List<OrderResponse> responses = new ArrayList<>();

        for (Orders order:orders){
            responses.add((order.convertToResponse()));
        }

        Collections.reverse(responses);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/order/approval/{id}")
    public ResponseEntity<OrderResponse> approvalOrder(@RequestHeader("Authorization") String jwt,
                                                       @PathVariable Integer id, @RequestParam Integer status) throws  Exception{

        return new ResponseEntity<>(orderService.approvalOrder(id, status).convertToResponse(), HttpStatus.OK);
    }

    @GetMapping("/supplier/all")
    public ResponseEntity<List<SupplierResponse>> getAll(@RequestHeader("Authorization") String jwt) throws Exception {
        List<Supplier> suppliers = suppierService.findAll();
        List<SupplierResponse> responses = new ArrayList<>();
        for (Supplier supplier:suppliers){
            responses.add((supplier.convertToResponse()));
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/supplier/{id}")
    public ResponseEntity<SupplierResponse> getDetail(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws Exception{
        Supplier supplier = suppierService.findByID(id);
        return new ResponseEntity<>(supplier.convertToResponse(), HttpStatus.OK);
    }


    @GetMapping("/receipt/all")
    public ResponseEntity<List<ReceiptResponse>>  getALl(@RequestHeader("Authorization") String jwt) throws Exception {
        List<Receipt> receipts = receiptService.getAll();
        List<ReceiptResponse> responses = new ArrayList<>();
        for(Receipt receipt:receipts){
            responses.add(receipt.convertToResponse());
        }
        Collections.reverse(responses);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/receipt/{id}")
    public ResponseEntity<ReceiptResponse> getByID(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws Exception{
        return new ResponseEntity<>(receiptService.findByID(id).convertToResponse(), HttpStatus.OK);
    }


    @GetMapping("/product/supplier/{id}")
    public ResponseEntity<List<ProductInSupplierResponse>> getAllBySupplier(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws Exception{
        List<Product> products = productServiceImp.findBySupplier(id);

        List<ProductInSupplierResponse> responses = new ArrayList<>();
        for (Product product : products){
            ProductInSupplierResponse response = product.convertToResponseSupplier();
            Price price = priceServices.getPriceByProductTime(product.getId(), LocalDateTime.now()).get(0);
            response.setPriceSale(price.getPrice_sale());
            response.setPricePurchase(price.getPrice_purchase());
            responses.add(response);
        }

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
    @PostMapping("/receipt")
    public ResponseEntity<ReceiptResponse> createReceipt(@RequestHeader("Authorization") String jwt,@RequestBody ReceiptRequest request) throws Exception{
        Receipt receipt = receiptService.createReceipt(request, jwt);
        return new ResponseEntity<>(receipt.convertToResponse(), HttpStatus.OK);
    }

}
