package com.example.TTTotNghiep.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

@Repository
public class CustomProductRepositoryImpl implements CustomProductRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteProductPhotoByID(Integer id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("delete_product_photo");

        query.registerStoredProcedureParameter("id", Integer.class, jakarta.persistence.ParameterMode.IN);

        query.setParameter("id", id);
        query.execute();


    }
}
