package com.example.TTTotNghiep.Service;


import com.example.TTTotNghiep.Repository.CommuneRepository;
import com.example.TTTotNghiep.Repository.DistrictRepository;
import com.example.TTTotNghiep.Repository.ProvinceRepository;
import com.example.TTTotNghiep.model.Commune;
import com.example.TTTotNghiep.model.District;
import com.example.TTTotNghiep.model.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommuneServices {
    @Autowired
    private CommuneRepository communeRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    public List<Province> getAllProvince(){
        return provinceRepository.findAll();
    }

    public List<District> getDistrictByProvince(String provinceid) throws Exception{
        Optional<Province> province = provinceRepository.findById(provinceid);
        if(province.isEmpty()) {
            throw new Exception("Invalid ProvinceID");
        }
        return districtRepository.findByProvince(province.get().getMatp());
    }

    public List<Commune> getCommuneByProvince(String districtid) throws Exception{
        Optional<District> district = districtRepository.findById(districtid);
        if(district.isEmpty()){
            throw new Exception("Invalid District");
        }

        return communeRepository.findBymaqh(district.get());
    }
}
