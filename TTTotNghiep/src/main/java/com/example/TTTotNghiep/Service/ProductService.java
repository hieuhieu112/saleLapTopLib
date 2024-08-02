package com.example.TTTotNghiep.Service;

import com.example.TTTotNghiep.Request.ProductRequest;
import com.example.TTTotNghiep.Request.UserRequest;
import com.example.TTTotNghiep.dto.ProductDTO;
import com.example.TTTotNghiep.model.Price;
import com.example.TTTotNghiep.model.Product;
import com.example.TTTotNghiep.model.User;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductService {

    public Product createProduct(ProductRequest request,@RequestHeader("Authorization") String jwt, Float priceSale, Float pricePurchase, LocalDateTime timeEnd) throws  Exception;

    public Product updateProduct(Integer id, ProductRequest request) throws  Exception;

    public void deleteProduct(Integer id) throws  Exception;

    public List<Product> getAllProduct();

    public List<Product> searchProduct(String search) throws  Exception;

    public Product findByID(Integer ID) throws  Exception;

    public List<Product> findByCategory(Integer id) throws  Exception;

    public ProductDTO addToFavorite(Integer id, User user) throws  Exception;

    public void changeQuantityFromCard(Integer id, String jwt, Integer quan) throws  Exception;

    public Price changPrice(Integer id, Float priceSale, Float pricePurchase, LocalDateTime start, LocalDateTime end) throws Exception;

    public void removeFromCart(Integer id, String jwt) throws  Exception;

    public void buyOrder(Integer idproduct, Integer quantity) throws Exception;

    public List<Product> findBySupplier(Integer id) throws Exception;
}
