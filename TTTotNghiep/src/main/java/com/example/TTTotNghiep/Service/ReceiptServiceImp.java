package com.example.TTTotNghiep.Service;

import com.example.TTTotNghiep.Repository.ReceiptDetailRepository;
import com.example.TTTotNghiep.Repository.ReceiptRepository;
import com.example.TTTotNghiep.Request.ReceiptDetailRequest;
import com.example.TTTotNghiep.Request.ReceiptRequest;
import com.example.TTTotNghiep.model.Receipt;
import com.example.TTTotNghiep.model.ReceiptDetail;
import com.example.TTTotNghiep.model.Supplier;
import com.example.TTTotNghiep.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReceiptServiceImp implements ReceiptService{

    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private ReceiptDetailRepository receiptDetailRepository;

    @Autowired
    private UserServicesImp userServicesImp;
    @Autowired
    private ProductServiceImp productService;
    @Autowired
    private SuppierServiceImpl suppierService;

    @Override
    public List<Receipt> getAll() throws Exception {
        return receiptRepository.findAll();
    }

    @Override
    public List<Receipt> getByUserAndTime(String jwt, LocalDateTime start, LocalDateTime end) throws Exception {
        User user = userServicesImp.findUserByJwtToken(jwt);

        if(start == null) start = LocalDateTime.of(2023, 7, 21, 14, 35, 22);
        if(end == null) end = LocalDateTime.of(2100, 7, 21, 14, 35, 22);

        return receiptRepository.findByUserAndTime(user.getId(), start, end);
    }

    @Override
    public List<Receipt> getBySupplierAndTime(Integer id, LocalDateTime start, LocalDateTime end) throws Exception {

        if(start == null) start = LocalDateTime.of(2023, 7, 21, 14, 35, 22);
        if(end == null) end = LocalDateTime.of(2100, 7, 21, 14, 35, 22);

        return receiptRepository.findBySupplierAndTime(id, start, end);
    }

    @Override
    public Receipt createReceipt(ReceiptRequest request, String jwt) throws Exception {

        Receipt receipt = request.convertToModel(userServicesImp.findUserByJwtToken(jwt), suppierService.findByID(request.getSupplierid()));
        //List<ReceiptDetail> listDetail = new ArrayList<>();

        Receipt result = receiptRepository.save(receipt);

        for(ReceiptDetailRequest detail : request.getDetailList()){
            receiptDetailRepository.save(detail.convertToModel(productService.findByID(detail.getProductid()), result));
        }

        return result;
    }

    @Override
    public Receipt findByID(Integer id) throws Exception {
        Optional<Receipt> receipt = receiptRepository.findById(id);
        if(receipt.isEmpty()) throw  new Exception("Invalid ID Receipt");
        return receipt.get();
    }

    @Override
    public void deleteByID(Integer id) throws Exception {
        receiptRepository.delete(findByID(id));
    }

    @Override
    public Receipt editReceipt(Integer id, ReceiptRequest request, String jwt) throws Exception {

        Receipt receipt = findByID(id);

        if(request.getDateImport() != null) receipt.setDateImport(request.getDateImport());

        if(!request.getDescription().isBlank() || !request.getDescription().isEmpty()) receipt.setDescription(request.getDescription());

        if(request.getSupplierid().describeConstable().isPresent()) {
            receipt.setSupplier(suppierService.findByID(request.getSupplierid()));
        }


        return receiptRepository.save(receipt);
    }
}
