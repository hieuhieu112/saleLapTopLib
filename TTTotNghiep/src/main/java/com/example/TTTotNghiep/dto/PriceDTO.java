package com.example.TTTotNghiep.dto;

import com.example.TTTotNghiep.model.Price;
import com.example.TTTotNghiep.model.Product;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Embeddable
public class PriceDTO {
    private Float price_sale;
    private Float price_purchase;
    private LocalDateTime start_date;
    private Integer ProductID;


    public Price converToModel(Product product){
        Price price = new Price();
        price.setPrice_sale(getPrice_sale());
        price.setPrice_purchase(price_purchase);
        price.setEnd_date(null);
        price.setStart_date(start_date);
        price.setProduct(product);

        return price;
    }
}
