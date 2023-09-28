package com.shoeshelf.controller;

import com.shoeshelf.dto.CategoryDto;
import com.shoeshelf.exceptions.CategoryNotFoundExceptions;
import com.shoeshelf.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAll(){
        try {
            List<CategoryDto> allCategory = categoryService.getAllCategory();
            return ResponseEntity.ok(allCategory);
        }catch (CategoryNotFoundExceptions e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable String id){
        try {
            CategoryDto categoryDto = categoryService.getById(Integer.valueOf(id));
            return ResponseEntity.ok(categoryDto);
        }catch (CategoryNotFoundExceptions e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
