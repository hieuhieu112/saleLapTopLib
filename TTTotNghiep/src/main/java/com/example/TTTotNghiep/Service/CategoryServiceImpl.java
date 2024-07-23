package com.example.TTTotNghiep.Service;

import com.example.TTTotNghiep.Repository.CategoryRepository;
import com.example.TTTotNghiep.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(String name, String photo) throws  Exception {
        Category category = new Category();
        category.setName(name);
        category.setNameen(name);
        category.setPhoto(photo);
        categoryRepository.save(category);
        return category;
    }

    @Override
    public Category editCategory(String name, Integer id, String photo)  throws  Exception{
        Category category = getDetail(id);
        category.setName(name);
        category.setNameen(name);
        category.setPhoto(photo);
        categoryRepository.save(category);
        return category;
    }

    @Override
    public void deleteCategory(Integer id) throws  Exception {
        categoryRepository.delete(getDetail(id));
    }

    @Override
    public List<Category> findAll() throws  Exception{
        return categoryRepository.findAll();
    }

    @Override
    public Category getDetail(Integer id) throws  Exception{
        Optional<Category> category = categoryRepository.findById(id);

        if(category.isEmpty()){
            throw  new Exception("Invalid ID");
        }

        return category.get();
    }
}
