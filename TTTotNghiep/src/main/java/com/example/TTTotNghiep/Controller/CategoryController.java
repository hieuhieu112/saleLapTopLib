package com.example.TTTotNghiep.Controller;


import com.example.TTTotNghiep.Response.MessageResponse;
import com.example.TTTotNghiep.Service.CategoryServiceImpl;
import com.example.TTTotNghiep.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping("")
    public ResponseEntity<Category> createCategory(@RequestHeader("Authorization") String jwt, @RequestBody Category category)  throws Exception{
        return new ResponseEntity<>(categoryService.createCategory(category.getName(), category.getPhoto()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> editCategory(@RequestHeader("Authorization") String jwt, @RequestBody Category category, @PathVariable Integer id) throws Exception{
        return new ResponseEntity<>( categoryService.editCategory(category.getName(), id, category.getPhoto()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCategory(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) throws Exception{
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(new MessageResponse("Delete success"), HttpStatus.OK);
    }
}
