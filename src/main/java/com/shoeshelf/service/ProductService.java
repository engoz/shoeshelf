package com.shoeshelf.service;

import com.shoeshelf.domain.Category;
import com.shoeshelf.domain.Product;
import com.shoeshelf.dto.product.ProductCreateDto;
import com.shoeshelf.dto.product.ProductDto;
import com.shoeshelf.dto.product.ProductUpdateDto;
import com.shoeshelf.exceptions.CategoryNotFoundExceptions;
import com.shoeshelf.exceptions.InternelServerException;
import com.shoeshelf.exceptions.ProductExistException;
import com.shoeshelf.exceptions.ProductNotFoundException;
import com.shoeshelf.repository.CategoryRepository;
import com.shoeshelf.repository.ProductRepository;
import com.shoeshelf.util.CategoryDtoConverters;
import com.shoeshelf.util.ProductDtoConverters;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

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

    public ProductDto createProduct(ProductCreateDto dto) {
        if (dto == null)
            throw new NullPointerException();

        Product byName = productRepository.findByName(dto.getName());

        if (byName != null)
            throw new ProductExistException("Product is exist");

        Optional<Category> category = categoryRepository.findById(dto.getCategoryId());

        if(!category.isPresent())
            throw new CategoryNotFoundExceptions("Category not found");

        Product product = new Product();
        product.setName(dto.getName());
        product.setCategory(category.get());
        product.setBuyPrice(dto.getBuyPrice());
        product.setSellPrice(dto.getSellPrice());
        product.setQuantity(dto.getQuantity());
        product.setImageURL(dto.getImageURL());
        product.setDescription(dto.getDescription());
        productRepository.save(product);

        ProductDto productDto = ProductDtoConverters.convertProductToDto(product);

        return productDto;
    }


    public ProductDto update(ProductUpdateDto dto) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(dto.getId());
        if (productOptional.isEmpty()){
            throw new ProductNotFoundException("product not found with id :" + dto.getId());
        }

        Optional<Category> category = categoryRepository.findById(dto.getCategoryId());

        Product product = new Product();
        product.setName(dto.getName());
        product.setCategory(category.get());
        product.setBuyPrice(dto.getBuyPrice());
        product.setSellPrice(dto.getSellPrice());
        product.setQuantity(dto.getQuantity());
        product.setImageURL(dto.getImageURL());
        product.setDescription(dto.getDescription());
        product.setModifiedDate(LocalDateTime.now());
        productRepository.save(product);

        ProductDto productDto = ProductDtoConverters.convertProductToDto(product);
        return productDto;
    }

    public void deleteProduct(Integer id) {
        try {
            productRepository.deleteById(id);
        }catch (Exception ex){
            throw new InternelServerException(ex);
        }
    }
}
