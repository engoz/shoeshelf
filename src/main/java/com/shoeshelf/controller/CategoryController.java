package com.shoeshelf.controller;

import com.shoeshelf.dto.CategoryDto;
import com.shoeshelf.exceptions.CategoryNotFoundExceptions;
import com.shoeshelf.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto dto){
        try {
            CategoryDto categoryDto = categoryService.createCategory(dto);
            return ResponseEntity.ok(categoryDto);
        }catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/update")
    public ResponseEntity<CategoryDto> update(@RequestBody CategoryDto dto){
        try {
            CategoryDto categoryDto = categoryService.update(dto);
            return ResponseEntity.ok(categoryDto);
        }catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok().build();
        }catch (CategoryNotFoundExceptions ex){
            return ResponseEntity.internalServerError().build();
        }catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }
}
