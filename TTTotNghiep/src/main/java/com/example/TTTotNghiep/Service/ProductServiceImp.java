package com.example.TTTotNghiep.Service;


import com.example.TTTotNghiep.Repository.*;
import com.example.TTTotNghiep.Request.ProductRequest;
import com.example.TTTotNghiep.Request.UserRequest;
import com.example.TTTotNghiep.dto.ProductDTO;
import com.example.TTTotNghiep.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CommuneRepository communeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServicesImp userServices;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;


    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PriceServicesImpl priceServices;



    @Override
    public Product createProduct(ProductRequest request,@RequestHeader("Authorization") String jwt,
                                 Float priceSale, Float pricePurchase) throws Exception {
        Optional<Category> category = categoryRepository.findById(request.getCategoryID());
        if(category.isEmpty()){
            throw new Exception("Invalid Category");
        }

        Optional<Supplier> supplier = supplierRepository.findById(request.getSupplierID());
        if(supplier.isEmpty()){
            throw  new Exception("Invalid Supplier");
        }

        Product product =  productRepository.save(request.convertToCreate(category.get(), supplier.get()));

        Price price = new Price();
        price.setPrice_purchase(pricePurchase);
        price.setPrice_sale(priceSale);
        price.setProduct(product);
        price.setStart_date(LocalDateTime.now());
        price.setEnd_date(null);

        priceServices.createPrice(price);

        return product;
    }

    @Override
    public Product updateProduct(Integer id, ProductRequest request) throws Exception {
        Product product = findByID(id);

        if(request.getCategoryID().describeConstable().isPresent()){
            Optional<Category> category = categoryRepository.findById(request.getCategoryID());
            if(category.isEmpty()){
                throw new Exception("Invalid Category");
            }
            product.setCategory(category.get());
        }
        if(request.getSupplierID().describeConstable().isPresent()){
            Optional<Supplier> supplier = supplierRepository.findById(request.getSupplierID());
            if(supplier.isEmpty()){
                throw  new Exception("Invalid Supplier");
            }
            product.setSupplier(supplier.get());
        }
        if(!request.getName().isEmpty()){
            product.setName(request.getName());
        }
        if(!request.getPhoto().isEmpty()){
            productRepository.deleteProductPhotoByID(product.getId());
            product.setPhoto(request.getPhoto());
        }
        if(request.getQuantity().describeConstable().isPresent()){
            product.setQuantity(request.getQuantity());
        }
        if(request.getDate() != null){
            product.setDate(request.getDate());
        }
        if(!request.getDescription().isEmpty()){
            product.setDescription(request.getDescription());
        }
        if(request.getStatus().describeConstable().isPresent()){
            product.setStatus(request.getStatus());
        }

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Integer id) throws Exception {
        productRepository.deleteProductPhotoByID(id);
        productRepository.delete(findByID(id));

    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> searchProduct(String search) throws Exception {
        return productRepository.searchProduct(search);
    }

    @Override
    public Product findByID(Integer ID) throws Exception {
        Optional<Product> product = productRepository.findById(ID);
        if(product.isEmpty()){
            throw new Exception("Invalid ID Product");
        }
        return product.get();
    }

    @Override
    public List<Product> findByCategory(Integer id) throws Exception {
        return productRepository.findByCategoryID(id);
    }

    @Override
    public ProductDTO addToFavorite(Integer id, User userRequest) throws Exception {

        ProductDTO dto =findByID(id).convertToDTO();
        if(userRequest.getFavorites().contains(id)){
            userRequest.getFavorites().remove(id);
        }else{
            userRequest.getFavorites().add(id);
        }
        userRepository.save(userRequest);
        return dto;
    }



    @Override
    public void changeQuantityFromCard(Integer id, String jwt, Integer quan) throws Exception {
        Product product = findByID(id);
        Orders cart = orderRepository.findCartByUserID(userServices.findUserByJwtToken(jwt).getEmail());

        for(OrderDetail orderDetail1 : orderDetailService.getAllByIDOrder(cart.getId())){
            if(Objects.equals(orderDetail1.getProduct().getId(), id)){
                if(orderDetail1.getQuantity() + quan <= 0){
                    orderDetailRepository.delete(orderDetail1);
                    return ;
                }else{
                    orderDetail1.setQuantity(orderDetail1.getQuantity() + quan);
                    orderDetailRepository.save(orderDetail1);
                    return ;
                }
            }
        }

        if(quan>0){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product);
            orderDetail.setOrder(cart);
            orderDetail.setUnitprice(priceServices.getPriceByProductTime(id, LocalDateTime.now()).get(0).getPrice_sale());
            orderDetail.setQuantity(quan);
            orderDetailRepository.save(orderDetail);
        }
    }

    @Override
    public Price changPrice(Integer id, Float priceSale, Float pricePurchase, LocalDateTime start, LocalDateTime end) throws Exception {
        Price priceDate = new Price();

        Product product = findByID(id);
        priceDate.setEnd_date(end);
        priceDate.setStart_date(start);
        priceDate.setProduct(product);
        priceDate.setPrice_sale(priceSale);
        priceDate.setPrice_purchase(pricePurchase);


        return priceServices.createPrice(priceDate);
    }

    @Override
    public void removeFromCart(Integer id, String jwt) throws Exception {

        Orders cart = orderRepository.findCartByUserID(userServices.findUserByJwtToken(jwt).getEmail());
        for(OrderDetail orderDetail1 : orderDetailService.getAllByIDOrder(cart.getId())){
            if(Objects.equals(orderDetail1.getProduct().getId(), id)){
                orderDetailRepository.delete(orderDetail1);
                return;
            }
        }

        throw new Exception("Can not find Product in cart");
    }

    @Override
    public void buyOrder(Integer idproduct, Integer quantity) throws Exception {
        Product product = findByID(idproduct);

        if(product.getQuantity() < quantity && quantity > 0) throw  new Exception("Error quantity to order");

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

    @Override
    public void checkQuantityOrder(Integer idproduct, Integer quantity) throws Exception {
        Product product = findByID(idproduct);
        if(product.getQuantity() < quantity && quantity > 0) throw  new Exception("Error quantity to order");
        productRepository.save(product);
    }

    @Override
    public List<Product> findBySupplier(Integer id) throws Exception {
//        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
//        if(optionalSupplier.isEmpty()) throw  new Exception("Invalid Supplier ID");
        return productRepository.findBySupplierID(id);
    }


}
