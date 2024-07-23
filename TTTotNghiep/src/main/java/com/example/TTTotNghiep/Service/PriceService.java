package com.example.TTTotNghiep.Service;

import com.example.TTTotNghiep.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceService {
    public List<Price> getPriceByProduct(Integer id) throws Exception;
    public List<Price> getPriceByProductTime(Integer id, LocalDateTime time) throws Exception;
    public Price updatePrice(Integer id, Price price) throws Exception;
    public void deletePrice(Integer id)throws Exception;
    public Price createPrice(Price price) throws Exception;
}
