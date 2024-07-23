package com.example.TTTotNghiep.Service;

import com.example.TTTotNghiep.model.Category;

import java.util.List;

public interface CategoryService {
    public Category createCategory(String name, String photo) throws  Exception;
    public Category editCategory(String name, Integer id, String photo) throws  Exception;
    public void deleteCategory(Integer id)throws  Exception;
    public List<Category> findAll() throws  Exception;
    public Category getDetail(Integer id) throws  Exception;
}
