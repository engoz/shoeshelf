package com.shoeshelf.service;

import com.shoeshelf.domain.Product;
import com.shoeshelf.dto.ProductDto;
import com.shoeshelf.exceptions.InternelServerException;
import com.shoeshelf.exceptions.ProductNotFoundException;
import com.shoeshelf.repository.ProductRepository;
import com.shoeshelf.util.CategoryDtoConverters;
import com.shoeshelf.util.ProductDtoConverters;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;

    public List<ProductDto> getAllProducts() throws ProductNotFoundException {

        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> prodcuts = productRepository.findAll();
        if (prodcuts.isEmpty()){
            throw new ProductNotFoundException("Product not found ");
        }
        for (Product product : prodcuts) {
            ProductDto productDto = ProductDtoConverters.convertProductToDto(product);
            productDtos.add(productDto);
        }
        return productDtos;

    }

    public ProductDto getById(Integer productId) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty())
            throw new ProductNotFoundException("Product id is invalid " + productId);

        return ProductDtoConverters.convertProductToDto(optionalProduct.get());
    }

    public ProductDto createProduct(ProductDto dto) {
        if (dto == null)
            throw new NullPointerException();

        Product product = new Product();
        product.setName(dto.getName());
        product.setCategory(CategoryDtoConverters.convertDtoToCategory(dto.getCategoryDto()));
        product.setDescription(dto.getDescription());
        productRepository.save(product);
        dto.setId(product.getId());

        return dto;
    }


    public ProductDto update(ProductDto dto) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(dto.getId());
        if (productOptional.isEmpty()){
            throw new ProductNotFoundException("product not found with id :" + dto.getId());
        }

        Product product = new Product();
        product.setName(dto.getName());
        product.setCategory(CategoryDtoConverters.convertDtoToCategory(dto.getCategoryDto()));
        product.setDescription(dto.getDescription());
        productRepository.save(product);
        return dto;
    }

    public void deleteProduct(Integer id) {
        try {
            productRepository.deleteById(id);
        }catch (Exception ex){
            throw new InternelServerException(ex);
        }
    }
}
