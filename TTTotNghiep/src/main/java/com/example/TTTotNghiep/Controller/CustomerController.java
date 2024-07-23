package com.example.TTTotNghiep.Controller;

import com.example.TTTotNghiep.Response.MessageResponse;
import com.example.TTTotNghiep.Service.CategoryServiceImpl;
import com.example.TTTotNghiep.Service.PriceServicesImpl;
import com.example.TTTotNghiep.Service.ProductServiceImp;
import com.example.TTTotNghiep.Service.UserServicesImp;
import com.example.TTTotNghiep.dto.ProductDTO;
import com.example.TTTotNghiep.model.Category;
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
@RequestMapping("/")
public class CustomerController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private ProductServiceImp productServiceImp;

    @Autowired
    private UserServicesImp userServicesImp;

    @Autowired
    private PriceServicesImpl priceServices;

    @GetMapping("category/all")
    public ResponseEntity<List<Category>> getAll() throws Exception {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("category/{id}")
    public ResponseEntity<Category> getDetail(@PathVariable Integer id) throws Exception{
        return new ResponseEntity<>(categoryService.getDetail(id), HttpStatus.OK) ;
    }

    @GetMapping("product/all")
    public ResponseEntity<List<ProductDTO>> getALlProduct() throws Exception {
        List<Product> products = productServiceImp.getAllProduct();
        List<ProductDTO> dtoList = new ArrayList<>();

        for (Product product : products){
            ProductDTO dto = product.convertToDTO();
            dto.setPrice(priceServices.getPriceByProductTime(product.getId(), LocalDateTime.now()).get(0).getPrice_sale());
            dtoList.add(dto);
        }

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("product/search")
    public ResponseEntity<List<ProductDTO>> searchProduct(@RequestParam String keyword) throws Exception {
        List<Product> products = productServiceImp.searchProduct(keyword);

        List<ProductDTO> dtoList = new ArrayList<>();

        for (Product product : products){
            ProductDTO dto = product.convertToDTO();
            dto.setPrice(priceServices.getPriceByProductTime(product.getId(), LocalDateTime.now()).get(0).getPrice_sale());
            dtoList.add(dto);
        }

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("product/{id}")
    public ResponseEntity<ProductDTO> getDetailProduct(@PathVariable Integer id) throws Exception {

        ProductDTO products = productServiceImp.findByID(id).convertToDTO();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("product/category/{id}")
    public ResponseEntity<List<ProductDTO>> getListProductByCategory(@PathVariable Integer id) throws Exception {
        List<Product> products = productServiceImp.findByCategory(id);

        List<ProductDTO> dtoList = new ArrayList<>();

        for (Product product : products){
            ProductDTO dto = product.convertToDTO();
            dto.setPrice(priceServices.getPriceByProductTime(product.getId(), LocalDateTime.now()).get(0).getPrice_sale());
            dtoList.add(dto);
        }

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

}
