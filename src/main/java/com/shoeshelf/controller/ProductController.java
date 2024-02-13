package com.shoeshelf.controller;

import com.shoeshelf.dto.product.ProductCreateDto;
import com.shoeshelf.dto.product.ProductDto;
import com.shoeshelf.dto.product.ProductUpdateDto;
import com.shoeshelf.exceptions.CategoryNotFoundExceptions;
import com.shoeshelf.exceptions.ProductNotFoundException;
import com.shoeshelf.service.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {


    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAll(){
        try {
            List<ProductDto> allCategory = productService.getAllProducts();
            return ResponseEntity.ok(allCategory);
        }catch (ProductNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable String id){
        try {
            ProductDto productDto = productService.getById(Integer.valueOf(id));
            return ResponseEntity.ok(productDto);
        }catch (CategoryNotFoundExceptions e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDto> create(@RequestBody ProductCreateDto dto){
        try {
            ProductDto productDto = productService.createProduct(dto);
            return ResponseEntity.ok(productDto);
        }catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDto> update(@RequestBody ProductUpdateDto dto){
        try {
            ProductDto productDto = productService.update(dto);
            return ResponseEntity.ok(productDto);
        }catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }
}
