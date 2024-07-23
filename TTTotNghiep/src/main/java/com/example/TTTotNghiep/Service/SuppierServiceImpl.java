package com.example.TTTotNghiep.Service;

import com.example.TTTotNghiep.Repository.CommuneRepository;
import com.example.TTTotNghiep.Repository.SupplierRepository;
import com.example.TTTotNghiep.Request.SupplierRequest;
import com.example.TTTotNghiep.model.Commune;
import com.example.TTTotNghiep.model.Price;
import com.example.TTTotNghiep.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SuppierServiceImpl implements SupplierService{
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CommuneRepository communeRepository;


    @Override
    public Supplier createSupplier(SupplierRequest request) throws Exception{
        Optional<Commune> commune = communeRepository.findById(request.getCommuneID());
        if(commune.isEmpty()) throw new Exception("Invalid commune");
        Supplier supplier = request.convertToModel(commune.get());

        supplierRepository.save(supplier);
        return supplier;
    }

    @Override
    public void deleteSupplier(Integer id) throws Exception {
        Supplier supplier = findByID(id);

        supplierRepository.delete(supplier);
    }


    @Override
    public Supplier editSupplier(Integer id, SupplierRequest request)  throws Exception{
        Supplier supplier = findByID(id);

        Optional<Commune> commune = communeRepository.findById(request.getCommuneID());

        if(commune.isEmpty()){
            throw  new Exception("Invalid commune");
        }else supplier.setCommune(commune.get());

        if(!request.getName().isEmpty() && !request.getName().isBlank()) supplier.setName(request.getName());

        if(!request.getLogo().isEmpty() && !request.getLogo().isBlank()) supplier.setLogo(request.getLogo());

        if(!request.getEmail().isEmpty() && !request.getEmail().isBlank()) supplier.setEmail(request.getEmail());

        if(!request.getNumberPhone().isBlank() && !request.getNumberPhone().isEmpty()) supplier.setNumberPhone(request.getNumberPhone());

        if(!request.getAddressdescription().isEmpty() && !request.getAddressdescription().isBlank()) supplier.setAddressDescription(request.getAddressdescription());


        return supplierRepository.save(supplier);
    }

    @Override
    public List<Supplier> findAll() throws Exception {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier findByID(Integer id) throws Exception {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if(supplier.isEmpty())throw  new Exception("Invalid SUpllier ID");

        return supplier.get();
    }
}
