package com.shoeshelf.util;

import com.shoeshelf.domain.Product;
import com.shoeshelf.dto.product.ProductDto;

public class ProductDtoConverters {

    public static ProductDto convertProductToDto(Product product) {
        if (product == null)
            throw new NullPointerException();

        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCategoryDto(CategoryDtoConverters.convertDtoFromCategory(product.getCategory()));
        dto.setBuyPrice(product.getBuyPrice());
        dto.setSellPrice(product.getSellPrice());
        dto.setQuantity(product.getQuantity());
        dto.setImageURL(product.getImageURL());
        dto.setDescription(product.getDescription());
        dto.setActive(product.isActive());
        return dto;

    }
}
