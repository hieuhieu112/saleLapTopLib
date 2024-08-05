package com.example.TTTotNghiep.Service;

import com.example.TTTotNghiep.Repository.PriceRepository;
import com.example.TTTotNghiep.model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PriceServicesImpl implements PriceService {
    @Autowired
    private PriceRepository priceRepository;

    @Override
    public List<Price> getPriceByProduct(Integer id) {
        return priceRepository.findByProductID(id);
    }

    @Override
    public List<Price> getPriceByProductTime(Integer id, LocalDateTime time) {
        return priceRepository.findPriceByTimeAndProduct(id, time);
    }



    @Override
    public Price updatePrice(Integer id, Price price) throws Exception {
        Price priceUpdate = getByID(id);
        if(price.getPrice_purchase().describeConstable().isPresent()){
            priceUpdate.setPrice_purchase(price.getPrice_purchase());
        }

        if(price.getPrice_sale().describeConstable().isPresent()){
            priceUpdate.setPrice_sale(price.getPrice_sale());
        }

        if(price.getStart_date() != null){
            priceUpdate.setStart_date(price.getStart_date());
        }

        if(price.getEnd_date() != null){
            priceUpdate.setEnd_date(price.getEnd_date());
        }

        return priceRepository.save(priceUpdate);
    }

    public Price getByID(Integer id) throws Exception{
        Optional<Price> price = priceRepository.findById(id);
        if(price.isEmpty()){
            throw  new Exception("Invalid Price ID");
        }

        return price.get();
    }

    @Override
    public void deletePrice(Integer id) throws Exception {
        priceRepository.deleteById(id);
    }

    @Override
    public Price createPrice(Price price) throws Exception {
        return priceRepository.save(price);
    }

    @Override
    public Price endPrice(Integer productid) throws Exception {
        List<Price> priceNowList = getPriceByProductTime(productid, LocalDateTime.now());
        Price priceNow = priceNowList.get(0);
        priceNow.setEnd_date(LocalDateTime.now());
        priceRepository.save(priceNow);

        return priceNow;
    }
}
