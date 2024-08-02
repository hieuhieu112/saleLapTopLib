package com.example.TTTotNghiep.Controller;

import com.example.TTTotNghiep.Request.ProductRequest;
import com.example.TTTotNghiep.Response.MessageResponse;
import com.example.TTTotNghiep.Service.PriceServicesImpl;
import com.example.TTTotNghiep.Service.ProductServiceImp;
import com.example.TTTotNghiep.Service.UserServicesImp;
import com.example.TTTotNghiep.dto.ProductDTO;
import com.example.TTTotNghiep.model.Price;
import com.example.TTTotNghiep.model.Product;
import com.example.TTTotNghiep.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/product")
public class ProductController {

    @Autowired
    private ProductServiceImp productServiceImp;
    @Autowired
    private UserServicesImp userServicesImp;
    @Autowired
    private PriceServicesImpl priceServices;


    @PostMapping("")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductRequest request,@RequestHeader("Authorization") String jwt,
                                                    @RequestParam Float priceSale, @RequestParam Float pricePurchase, @RequestParam LocalDateTime timeEnd) throws Exception {
        Product product = productServiceImp.createProduct(request,jwt, priceSale, pricePurchase, timeEnd);
        ProductDTO dto = product.convertToDTO();
        Thread.sleep(500);
        dto.setPrice(priceServices.getPriceByProductTime(product.getId(), LocalDateTime.now()).get(0).getPrice_sale());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/supplier/{id}")
    public ResponseEntity<List<ProductDTO>> getAllBySupplier(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws Exception{
        List<Product> products = productServiceImp.findBySupplier(id);

        List<ProductDTO> dtoList = new ArrayList<>();
        for (Product product : products){
            ProductDTO dto = product.convertToDTO();
            dto.setPrice(priceServices.getPriceByProductTime(product.getId(), LocalDateTime.now()).get(0).getPrice_sale());
            dtoList.add(dto);
        }

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductRequest request, @PathVariable Integer id) throws Exception {
        ProductDTO product = productServiceImp.updateProduct(id,request).convertToDTO();
        product.setPrice(priceServices.getPriceByProductTime(id, LocalDateTime.now()).get(0).getPrice_sale());

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable Integer id) throws Exception {
        productServiceImp.deleteProduct(id);

        return new ResponseEntity<>(new MessageResponse("Delete product successfully"), HttpStatus.OK);
    }


}
