package com.example.TTTotNghiep.Controller;

import com.example.TTTotNghiep.Request.PriceRequest;
import com.example.TTTotNghiep.Request.ProductRequest;
import com.example.TTTotNghiep.Response.MessageResponse;
import com.example.TTTotNghiep.Response.PriceResponse;
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
import java.util.Collections;
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
                                                    @RequestParam Float priceSale, @RequestParam Float pricePurchase) throws Exception {
        Product product = productServiceImp.createProduct(request,jwt, priceSale, pricePurchase);
        ProductDTO dto = product.convertToDTO();
        Thread.sleep(500);
        Price price =priceServices.getPriceByProductTime(product.getId(), LocalDateTime.now()).get(0);
        dto.setPrice(price.getPrice_sale());
        dto.setPrice_import(price.getPrice_purchase());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/supplier/{id}")
    public ResponseEntity<List<ProductDTO>> getAllBySupplier(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws Exception{
        List<Product> products = productServiceImp.findBySupplier(id);

        List<ProductDTO> dtoList = new ArrayList<>();
        for (Product product : products){
            ProductDTO dto = product.convertToDTO();
            Price price = priceServices.getPriceByProductTime(product.getId(), LocalDateTime.now()).get(0);
            dto.setPrice(price.getPrice_sale());
            dto.setPrice_import(price.getPrice_purchase());
            dtoList.add(dto);
        }

        Collections.reverse(dtoList);

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestHeader("Authorization") String jwt, @RequestBody ProductRequest request, @PathVariable Integer id) throws Exception {
        ProductDTO product = productServiceImp.updateProduct(id,request).convertToDTO();
        Price price = priceServices.getPriceByProductTime(id, LocalDateTime.now()).get(0);
        product.setPrice(price.getPrice_sale());
        product.setPrice_import(price.getPrice_purchase());

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteProduct(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws Exception {
        productServiceImp.deleteProduct(id);

        return new ResponseEntity<>(new MessageResponse("Delete product successfully"), HttpStatus.OK);
    }

    @GetMapping("/price/{id}")
    public ResponseEntity<List<PriceResponse>> getListPriceProduct(@PathVariable Integer id, @RequestHeader("Authorization") String jwt) throws  Exception{
        List<Price> prices = priceServices.getPriceByProduct(id);

        List<PriceResponse> priceResponses = new ArrayList<>();
        for(Price price: prices){
            priceResponses.add(price.convertToResponse());
        }

        return new ResponseEntity<>(priceResponses, HttpStatus.OK);
    }

    @PostMapping("/price/{id}")
    public ResponseEntity<PriceResponse> createPrice(@PathVariable Integer id, @RequestBody PriceRequest request, @RequestHeader("Authorization") String jwt) throws  Exception{
        Product product = productServiceImp.findByID(id);
        priceServices.endPrice(id);
        Price price = request.converToModel(product);

        Price saved = priceServices.createPrice(price);

        return new ResponseEntity<>(saved.convertToResponse(), HttpStatus.OK);
    }
}
