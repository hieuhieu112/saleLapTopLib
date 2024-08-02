package com.example.TTTotNghiep.model;


import com.example.TTTotNghiep.Response.ReceiptDetailResponse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReceiptDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer quantity;
    private Float unitprice;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

    public ReceiptDetailResponse convertToResponse(){
        ReceiptDetailResponse response = new ReceiptDetailResponse();
        response.setId(id);
        response.setQuantity(quantity);
        response.setUnitprice(unitprice);
        response.setProduct(product.getName());

        return response;
    }
}
