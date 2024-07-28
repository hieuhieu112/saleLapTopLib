package com.example.TTTotNghiep.model;


import com.example.TTTotNghiep.Response.OrderDetailResponse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer quantity;
    private Float unitprice;

    @ManyToOne
    @JoinColumn(name = "DiscountID")
    private Discount_Detail discount;

    @ManyToOne
    @JoinColumn(name = "OrderID")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;


    public OrderDetailResponse convertToResponse(){
        OrderDetailResponse response = new OrderDetailResponse();
        response.setId(id);
        if(discount != null){
            response.setDiscount(discount.getDiscount().getDiscount());
        }else{
            response.setDiscount(0.0f);
        }

        response.setProduct(product.getName());
        response.setUnitprice(unitprice);
        response.setQuantity(quantity);
        response.setPhoto(product.getPhoto());

        return response;
    }
}
