package com.example.TTTotNghiep.Request;


import com.example.TTTotNghiep.model.Product;
import com.example.TTTotNghiep.model.Receipt;
import com.example.TTTotNghiep.model.ReceiptDetail;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ReceiptDetailRequest {
    private Integer quantity;
    private Integer unitprice;
    private Integer productid;
//    private Integer receiptid;
    public ReceiptDetail convertToModel(Product product, Receipt receipt){
        ReceiptDetail receiptDetail = new ReceiptDetail();
        receiptDetail.setReceipt(receipt);
        receiptDetail.setProduct(product);
        receiptDetail.setUnitprice(getUnitprice());
        receiptDetail.setQuantity(getQuantity());

        return receiptDetail;
    }
}
